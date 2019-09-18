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
package io.github.tomaso2468.rpgonline.post.pack;

import io.github.tomaso2468.rpgonline.post.ColorEffectsShader;
import io.github.tomaso2468.rpgonline.post.DynamicHeatShader2;
import io.github.tomaso2468.rpgonline.post.FXAA;
import io.github.tomaso2468.rpgonline.post.FragmentExpose;
import io.github.tomaso2468.rpgonline.post.LowExpose;
import io.github.tomaso2468.rpgonline.post.MotionBlur;
import io.github.tomaso2468.rpgonline.post.MultiEffect;
import io.github.tomaso2468.rpgonline.post.PostEffect;
import io.github.tomaso2468.rpgonline.post.ToggledEffect;

@Deprecated
public class ConfigurableEffectsPack extends MultiEffect {
	public static final ConfigurableEffectsPack INSTANCE = new ConfigurableEffectsPack();
	
	private static ToggledEffect heatShader;
	private static ToggledEffect expose;
	private static ToggledEffect expose2;
	private static ToggledEffect motionBlur;
	private static ToggledEffect colorEffects;
	private static ToggledEffect fxaa;
	
	private ConfigurableEffectsPack() {
		super(create());
	}

	public static PostEffect[] create() {
		heatShader = new ToggledEffect(new DynamicHeatShader2());
		expose = new ToggledEffect(new FragmentExpose(), true);
		expose2 = new ToggledEffect(new LowExpose());
		motionBlur = new ToggledEffect(new MotionBlur(0.4f), true);
		colorEffects = new ToggledEffect(new ColorEffectsShader(1f /*s*/, 0f /*b*/, 1.1f /*c*/, 1.25f /*v*/, 0f /*h*/, 1f /*g*/), true);
		fxaa = new ToggledEffect(new FXAA());
		
		return new PostEffect[] {
				heatShader,
				expose,
				expose2,
				colorEffects,
				motionBlur,
				fxaa,
		};
	}

	public static DynamicHeatShader2 getHeatShader() {
		return (DynamicHeatShader2) heatShader.getEffect();
	}

	public static void setHeatShader(boolean b) {
		ConfigurableEffectsPack.heatShader.setState(b);
	}

	public static FragmentExpose getExpose() {
		return (FragmentExpose) expose.getEffect();
	}

	public static void setExpose(boolean b) {
		ConfigurableEffectsPack.expose.setState(b);
	}

	public static LowExpose getExpose2() {
		return (LowExpose) expose2.getEffect();
	}

	public static void setExpose2(boolean b) {
		ConfigurableEffectsPack.expose2.setState(b);
	}

	public static MotionBlur getMotionBlur() {
		return (MotionBlur) motionBlur.getEffect();
	}

	public static void setMotionBlur(boolean b) {
		ConfigurableEffectsPack.motionBlur.setState(b);
	}

	public static ColorEffectsShader getColorEffects() {
		return (ColorEffectsShader) colorEffects.getEffect();
	}

	public static void setColorEffects(boolean b) {
		ConfigurableEffectsPack.colorEffects.setState(b);
	}

	public static FXAA getFxaa() {
		return (FXAA) fxaa.getEffect();
	}

	public static void setFxaa(boolean b) {
		ConfigurableEffectsPack.fxaa.setState(b);
	}
}
