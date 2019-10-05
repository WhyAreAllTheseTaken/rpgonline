/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.threading;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.newdawn.slick.util.Log;

/**
 * A class that acts as a constantly running thread pool that tasks can be submitted to. This class will appear terminated until new tasks are submitted.
 * @author Tomaso2468
 *
 */
public class ContinuousPool implements ExecutorService {
	/**
	 * The queue of tasks.
	 */
	private Queue<Callable<?>> tasks = new LinkedList<>();
	/**
	 * Determines if a shutdown was started.
	 */
	private boolean shutdown = false;
	/**
	 * The pool of threads.
	 */
	private Thread[] threads;
	
	/**
	 * Constructs a new ContinuousPool.
	 * @param tc The number of threads to use.
	 * @param sleepDelay The delay until a thread will sleep if no tasks are available.
	 * @param sleepTime The time a thread will sleep for before checking for more tasks.
	 */
	public ContinuousPool(int tc, long sleepDelay, long sleepTime) {
		threads = new Thread[tc];
		
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread() {
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					
					while (!shutdown) {
						Callable<?> next = getNext();
						if (next != null) {
							time = System.currentTimeMillis();
							
							try {
								next.call();
							} catch (Exception e) {
								Log.error("Error in ConinuousPool task", e);
							}
						}
						
						if (System.currentTimeMillis() > time + sleepDelay) {
							try {
								Thread.sleep(sleepTime);
							} catch (InterruptedException e) {
								// Assume shutdown
							}
						}
					}
				}
			};
		}
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
	}
	
	/**
	 * Gets the next available task in the queue.
	 * @return A callable object or null if no tasks are available.
	 */
	private synchronized Callable<?> getNext() {
		return tasks.poll();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void execute(Runnable command) {
		submit(command);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void shutdown() {
		shutdown = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized List<Runnable> shutdownNow() {
		shutdown();
		return new ArrayList<Runnable>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized boolean isShutdown() {
		return shutdown;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized boolean isTerminated() {
		return tasks.size() == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T> Future<T> submit(Callable<T> task) {
		SetableFuture<T> future = new SetableFuture<T>();
		
		Callable<T> task2 = new Callable<T>() {

			@Override
			public T call() throws Exception {
				T t = task.call();
				future.setResult(t);
				return t;
			}
			
		};
		future.register(task2);
		
		tasks.add(task2);
		
		return future;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T> Future<T> submit(Runnable task, T result) {
		return submit(new Callable<T>() {
			@Override
			public T call() throws Exception {
				return result;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized Future<?> submit(Runnable task) {
		return submit(task, (Void) null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
		
		for (Callable<T> c : tasks) {
			futures.add(submit(c));
		}
		
		while (true) {
			boolean complete = true;
			
			for (Future<T> f : futures) {
				if (!f.isDone()) {
					complete = false;
				}
			}
			
			if (complete) break;
		}
		
		return futures;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		long time = System.currentTimeMillis() + unit.toMillis(timeout);
		
		List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
		
		for (Callable<T> c : tasks) {
			futures.add(submit(c));
		}
		
		while (true) {
			boolean complete = true;
			
			for (Future<T> f : futures) {
				if (!f.isDone()) {
					complete = false;
				}
			}
			
			if (complete || time > System.currentTimeMillis()) {
				break;
			}
			
			Thread.yield();
		}
		
		return futures;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
		
		for (Callable<T> c : tasks) {
			futures.add(submit(c));
		}
		
		Future<T> success = null;
		
		while (true) {
			boolean complete = false;
			
			for (Future<T> f : futures) {
				if (f.isDone()) {
					complete = true;
					success = f;
				}
			}
			
			if (complete) break;
		}

		for (Future<T> f : futures) {
			if (f != success) {
				f.cancel(true);
			}
		}
		
		return success.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		long time = System.currentTimeMillis() + unit.toMillis(timeout);
		
		List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
		
		for (Callable<T> c : tasks) {
			futures.add(submit(c));
		}
		
		Future<T> success = null;
		
		while (true) {
			boolean complete = false;
			
			for (Future<T> f : futures) {
				if (f.isDone()) {
					complete = true;
					success = f;
				}
			}
			
			if (complete || time > System.currentTimeMillis()) {
				break;
			}
		}

		for (Future<T> f : futures) {
			if (f != success) {
				f.cancel(true);
			}
		}
		
		if (success == null) {
			return null;
		}
		return success.get();
	}

	/**
	 * A future implementation for this pool.
	 * @author Tomas
	 *
	 * @param <T> The type of the future.
	 */
	private final class SetableFuture<T> implements Future<T> {
		/**
		 * The task.
		 */
		private Callable<T> task;
		/**
		 * Determines if the task was cancelled.
		 */
		private boolean cancelled = false;
		/**
		 * Determines if the task is done.
		 */
		private boolean done = false;
		/**
		 * The result of the task.
		 */
		private T result;
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			cancelled = true;
			return tasks.remove(task);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isCancelled() {
			return cancelled;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isDone() {
			return done;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public T get() throws InterruptedException, ExecutionException {
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			return result;
		}
		
		/**
		 * Sets the result of the task.
		 * @param t A object of type T
		 */
		public void setResult(T t) {
			result = t;
			done = true;
		}
		
		/**
		 * Registers a callable for this task.
		 * @param c A callable object.
		 */
		public void register(Callable<T> c) {
			this.task = c;
		}
	}
}
