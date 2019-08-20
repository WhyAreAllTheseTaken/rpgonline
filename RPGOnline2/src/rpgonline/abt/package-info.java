
/**
 * <p>
 * A system of hierarchical tags used to store data in a binary form.
 * </p>
 * <p>
 * Although an ABT file has the file extension {@code .abt} this isn't needed
 * and the library will parse any stream which starts with a valid header.
 * </p>
 * <p>
 * Strings in this library are stored in UTF-8 format with an integer value
 * preceding them to store the length (in bytes) of the string. When storing
 * data is best to keep the length under the size of a signed integer of the
 * specified size as some systems don't support unsigned integers over a certain
 * size. For the rest of this documentation string sizes will be denoted as
 * "{@code nbit string}".
 * </p>
 * </p>
 * 
 * <p>
 * <strong>File Structure Definitions</strong> <br>
 * This section includes definitions for the structure of an ABT file. When in a
 * list these must be kept in the order shown (unless stated).
 * </p>
 * 
 * <p>
 * ABT Document:
 * <ul>
 * <li>Header</li>
 * <li>TagGroup</li>
 * </ul>
 * The first tag group acts as the root of the document and holds all tags
 * within it. The name of this tag does not matter but should usually be "root".
 * </p>
 * 
 * <p>
 * Header:
 * <ul>
 * <li>4 byte header: 0x41425431</li>
 * <li>Version: 16bit signed int</li>
 * <li>Application ID: 8bit string</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Tag:
 * <ul>
 * <li>Tag type ID: 8bit integer</li>
 * <li>Tag name: 16bit string</li>
 * <li>Tag data: various formats</li>
 * </ul>
 * <i>Note: the tag with ID {@code 0x0} is special and marks the end of a tag
 * group. It only has an ID and has no name or data and is 1 byte in length.</i>
 * </p>
 * 
 * <p>
 * <strong>Tag Data Formats</strong>
 * </p>
 * <table>
 * <tr>
 * <th>Tag ID (Hex)</th>
 * <th>Name</th>
 * <th>Java Class</th>
 * <th>Storage format</th>
 * <th>Used to store</th>
 * <th>Notes</th>
 * </tr>
 * 
 * <tr>
 * <td>0x00</td>
 * <td>null</td>
 * <td>N/A</td>
 * <td>N/A</td>
 * <td>N/A</td>
 * <td>This tag only has an ID and is not converted to an object when read.</td>
 * </tr>
 * 
 * <tr>
 * <td>0x01</td>
 * <td>group</td>
 * <td>TagGroup</td>
 * <td>
 * <ul>
 * <li>Size: 64bit unsigned integer</li>
 * <li>Tags...</li>
 * <li>Null terminator</li>
 * </ul>
 * </td>
 * <td>List/Array</td>
 * <td>This tag hold other tags but does not have to maintain the same order. To
 * store ordered data, the names of the contained tags should be indexes.</td>
 * </tr>
 * 
 * <tr>
 * <td>0x02</td>
 * <td>byte</td>
 * <td>TagByte</td>
 * <td>
 * <ul>
 * <li>Value: 8bit integer</li>
 * </ul>
 * </td>
 * <td>byte</td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>0x03</td>
 * <td>short</td>
 * <td>TagShort</td>
 * <td>
 * <ul>
 * <li>Value: 16bit integer</li>
 * </ul>
 * </td>
 * <td>short</td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>0x04</td>
 * <td>int</td>
 * <td>TagInt</td>
 * <td>
 * <ul>
 * <li>Value: 32bit integer</li>
 * </ul>
 * </td>
 * <td>int</td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>0x05</td>
 * <td>long</td>
 * <td>TagLong</td>
 * <td>
 * <ul>
 * <li>Value: 64bit integer</li>
 * </ul>
 * </td>
 * <td>long</td>
 * <td></td>
 * </tr>
 *
 * <tr>
 * <td>0x08</td>
 * <td>float</td>
 * <td>TagFloat</td>
 * <td>
 * <ul>
 * <li>Value: 32bit float</li>
 * </ul>
 * </td>
 * <td>float</td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>0x09</td>
 * <td>double</td>
 * <td>TagDouble</td>
 * <td>
 * <ul>
 * <li>Value: 64bit float</li>
 * </ul>
 * </td>
 * <td>double</td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>0x0B</td>
 * <td>string16</td>
 * <td>TagStringShort</td>
 * <td>
 * <ul>
 * <li>Value: 16bit string</li>
 * </ul>
 * </td>
 * <td>String</td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>0x0C</td>
 * <td>string</td>
 * <td>TagString</td>
 * <td>
 * <ul>
 * <li>Value: 64bit string</li>
 * </ul>
 * </td>
 * <td>String</td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>0x18</td>
 * <td>char</td>
 * <td>TagChar</td>
 * <td>
 * <ul>
 * <li>Value: 16bit integer</li>
 * </ul>
 * </td>
 * <td>char</td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>0x20</td>
 * <td>bool</td>
 * <td>TagBoolean</td>
 * <td>
 * <ul>
 * <li>Value: byte (1 = true, 0 = false).</li>
 * </ul>
 * </td>
 * <td>boolean</td>
 * <td></td>
 * </tr>
 * 
 * </table>
 * <p>
 * Tag values of 0x06, 0x07, 0x0D, 0x0E, 0x0F, 0x10, 0x11, 0x12, 0x13, 0x14,
 * 0x15, 0x16, 0x17, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E and 0x1F are deprecated
 * (as of version 1) and were not included in this table many of these types
 * were array types and should be replaced with groups. The other types were big
 * data types and should be strings or groups.
 * </p>
 * 
 * @author Tomas
 */
package rpgonline.abt;