begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|openoffice
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|Layout
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * Utility methods for processing OO Writer documents.  */
end_comment

begin_class
DECL|class|OOUtil
specifier|public
class|class
name|OOUtil
block|{
comment|// TODO: temporarily removed, LibreOffice, Java 9
comment|//
comment|//    private static final String CHAR_STRIKEOUT = "CharStrikeout";
comment|//    private static final String CHAR_UNDERLINE = "CharUnderline";
comment|//    private static final String PARA_STYLE_NAME = "ParaStyleName";
comment|//    private static final String CHAR_CASE_MAP = "CharCaseMap";
comment|//    private static final String CHAR_POSTURE = "CharPosture";
comment|//    private static final String CHAR_WEIGHT = "CharWeight";
comment|//    private static final String CHAR_ESCAPEMENT_HEIGHT = "CharEscapementHeight";
comment|//    private static final String CHAR_ESCAPEMENT = "CharEscapement";
comment|//
comment|//    public enum Formatting {
comment|//        BOLD,
comment|//        ITALIC,
comment|//        SMALLCAPS,
comment|//        SUPERSCRIPT,
comment|//        SUBSCRIPT,
comment|//        UNDERLINE,
comment|//        STRIKEOUT,
comment|//        MONOSPACE
comment|//    }
comment|//
comment|//    private static final Pattern HTML_TAG = Pattern.compile("</?[a-z]+>");
comment|//
comment|//    private static final String UNIQUEFIER_FIELD = "uniq";
comment|//
comment|//    private OOUtil() {
comment|//        // Just to hide the public constructor
comment|//    }
comment|//
comment|//    /**
comment|//     * Insert a reference, formatted using a Layout, at the position of a given cursor.
comment|//     * @param text The text to insert in.
comment|//     * @param cursor The cursor giving the insert location.
comment|//     * @param layout The Layout to format the reference with.
comment|//     * @param parStyle The name of the paragraph style to use.
comment|//     * @param entry The entry to insert.
comment|//     * @param database The database the entry belongs to.
comment|//     * @param uniquefier Uniqiefier letter, if any, to append to the entry's year.
comment|//     */
comment|//    public static void insertFullReferenceAtCurrentLocation(XText text, XTextCursor cursor,
comment|//            Layout layout, String parStyle, BibEntry entry, BibDatabase database, String uniquefier)
comment|//                    throws UndefinedParagraphFormatException, UnknownPropertyException, PropertyVetoException,
comment|//                    WrappedTargetException, IllegalArgumentException {
comment|//
comment|//        // Backup the value of the uniq field, just in case the entry already has it:
comment|//        Optional<String> oldUniqVal = entry.getField(UNIQUEFIER_FIELD);
comment|//
comment|//        // Set the uniq field with the supplied uniquefier:
comment|//        if (uniquefier == null) {
comment|//            entry.clearField(UNIQUEFIER_FIELD);
comment|//        } else {
comment|//            entry.setField(UNIQUEFIER_FIELD, uniquefier);
comment|//        }
comment|//
comment|//        // Do the layout for this entry:
comment|//        String formattedText = layout.doLayout(entry, database);
comment|//
comment|//        // Afterwards, reset the old value:
comment|//        if (oldUniqVal.isPresent()) {
comment|//            entry.setField(UNIQUEFIER_FIELD, oldUniqVal.get());
comment|//        } else {
comment|//            entry.clearField(UNIQUEFIER_FIELD);
comment|//        }
comment|//
comment|//        // Insert the formatted text:
comment|//        OOUtil.insertOOFormattedTextAtCurrentLocation(text, cursor, formattedText, parStyle);
comment|//    }
comment|//
comment|//    /**
comment|//     * Insert a text with formatting indicated by HTML-like tags, into a text at
comment|//     * the position given by a cursor.
comment|//     * @param text The text to insert in.
comment|//     * @param cursor The cursor giving the insert location.
comment|//     * @param lText The marked-up text to insert.
comment|//     * @param parStyle The name of the paragraph style to use.
comment|//     * @throws WrappedTargetException
comment|//     * @throws PropertyVetoException
comment|//     * @throws UnknownPropertyException
comment|//     * @throws IllegalArgumentException
comment|//     */
comment|//    public static void insertOOFormattedTextAtCurrentLocation(XText text, XTextCursor cursor, String lText,
comment|//            String parStyle) throws UndefinedParagraphFormatException, UnknownPropertyException, PropertyVetoException,
comment|//                    WrappedTargetException, IllegalArgumentException {
comment|//
comment|//        XParagraphCursor parCursor = UnoRuntime.queryInterface(
comment|//                XParagraphCursor.class, cursor);
comment|//        XPropertySet props = UnoRuntime.queryInterface(
comment|//                XPropertySet.class, parCursor);
comment|//
comment|//        try {
comment|//            props.setPropertyValue(PARA_STYLE_NAME, parStyle);
comment|//        } catch (IllegalArgumentException ex) {
comment|//            throw new UndefinedParagraphFormatException(parStyle);
comment|//        }
comment|//
comment|//        List<Formatting> formatting = new ArrayList<>();
comment|//        // We need to extract formatting. Use a simple regexp search iteration:
comment|//        int piv = 0;
comment|//        Matcher m = OOUtil.HTML_TAG.matcher(lText);
comment|//        while (m.find()) {
comment|//            String currentSubstring = lText.substring(piv, m.start());
comment|//            if (!currentSubstring.isEmpty()) {
comment|//                OOUtil.insertTextAtCurrentLocation(text, cursor, currentSubstring, formatting);
comment|//            }
comment|//            String tag = m.group();
comment|//            // Handle tags:
comment|//            if ("<b>".equals(tag)) {
comment|//                formatting.add(Formatting.BOLD);
comment|//            } else if ("</b>".equals(tag)) {
comment|//                formatting.remove(Formatting.BOLD);
comment|//            } else if ("<i>".equals(tag) || "<em>".equals(tag)) {
comment|//                formatting.add(Formatting.ITALIC);
comment|//            } else if ("</i>".equals(tag) || "</em>".equals(tag)) {
comment|//                formatting.remove(Formatting.ITALIC);
comment|//            } else if ("<tt>".equals(tag)) {
comment|//                formatting.add(Formatting.MONOSPACE);
comment|//            } else if ("</tt>".equals(tag)) {
comment|//                formatting.remove(Formatting.MONOSPACE);
comment|//            } else if ("<smallcaps>".equals(tag)) {
comment|//                formatting.add(Formatting.SMALLCAPS);
comment|//            } else if ("</smallcaps>".equals(tag)) {
comment|//                formatting.remove(Formatting.SMALLCAPS);
comment|//            } else if ("<sup>".equals(tag)) {
comment|//                formatting.add(Formatting.SUPERSCRIPT);
comment|//            } else if ("</sup>".equals(tag)) {
comment|//                formatting.remove(Formatting.SUPERSCRIPT);
comment|//            } else if ("<sub>".equals(tag)) {
comment|//                formatting.add(Formatting.SUBSCRIPT);
comment|//            } else if ("</sub>".equals(tag)) {
comment|//                formatting.remove(Formatting.SUBSCRIPT);
comment|//            } else if ("<u>".equals(tag)) {
comment|//                formatting.add(Formatting.UNDERLINE);
comment|//            } else if ("</u>".equals(tag)) {
comment|//                formatting.remove(Formatting.UNDERLINE);
comment|//            } else if ("<s>".equals(tag)) {
comment|//                formatting.add(Formatting.STRIKEOUT);
comment|//            } else if ("</s>".equals(tag)) {
comment|//                formatting.remove(Formatting.STRIKEOUT);
comment|//            }
comment|//
comment|//            piv = m.end();
comment|//
comment|//        }
comment|//
comment|//        if (piv< lText.length()) {
comment|//            OOUtil.insertTextAtCurrentLocation(text, cursor, lText.substring(piv), formatting);
comment|//        }
comment|//
comment|//        cursor.collapseToEnd();
comment|//    }
comment|//
comment|//    public static void insertParagraphBreak(XText text, XTextCursor cursor) throws IllegalArgumentException {
comment|//        text.insertControlCharacter(cursor, ControlCharacter.PARAGRAPH_BREAK, true);
comment|//        cursor.collapseToEnd();
comment|//    }
comment|//
comment|//    public static void insertTextAtCurrentLocation(XText text, XTextCursor cursor, String string,
comment|//            List<Formatting> formatting)
comment|//                    throws UnknownPropertyException, PropertyVetoException, WrappedTargetException,
comment|//                    IllegalArgumentException {
comment|//        text.insertString(cursor, string, true);
comment|//        // Access the property set of the cursor, and set the currently selected text
comment|//        // (which is the string we just inserted) to be bold
comment|//        XPropertySet xCursorProps = UnoRuntime.queryInterface(
comment|//                XPropertySet.class, cursor);
comment|//        if (formatting.contains(Formatting.BOLD)) {
comment|//            xCursorProps.setPropertyValue(CHAR_WEIGHT,
comment|//                    com.sun.star.awt.FontWeight.BOLD);
comment|//        } else {
comment|//            xCursorProps.setPropertyValue(CHAR_WEIGHT,
comment|//                    com.sun.star.awt.FontWeight.NORMAL);
comment|//        }
comment|//
comment|//        if (formatting.contains(Formatting.ITALIC)) {
comment|//            xCursorProps.setPropertyValue(CHAR_POSTURE,
comment|//                    com.sun.star.awt.FontSlant.ITALIC);
comment|//        } else {
comment|//            xCursorProps.setPropertyValue(CHAR_POSTURE,
comment|//                    com.sun.star.awt.FontSlant.NONE);
comment|//        }
comment|//
comment|//        if (formatting.contains(Formatting.SMALLCAPS)) {
comment|//            xCursorProps.setPropertyValue(CHAR_CASE_MAP,
comment|//                    com.sun.star.style.CaseMap.SMALLCAPS);
comment|//        }        else {
comment|//            xCursorProps.setPropertyValue(CHAR_CASE_MAP,
comment|//                    com.sun.star.style.CaseMap.NONE);
comment|//        }
comment|//
comment|//        // TODO: the<monospace> tag doesn't work
comment|//        /*
comment|//        if (formatting.contains(Formatting.MONOSPACE)) {
comment|//            xCursorProps.setPropertyValue("CharFontPitch",
comment|//                            com.sun.star.awt.FontPitch.FIXED);
comment|//        }
comment|//        else {
comment|//            xCursorProps.setPropertyValue("CharFontPitch",
comment|//                            com.sun.star.awt.FontPitch.VARIABLE);
comment|//        } */
comment|//        if (formatting.contains(Formatting.SUBSCRIPT)) {
comment|//            xCursorProps.setPropertyValue(CHAR_ESCAPEMENT,
comment|//                    (byte) -101);
comment|//            xCursorProps.setPropertyValue(CHAR_ESCAPEMENT_HEIGHT,
comment|//                    (byte) 58);
comment|//        } else if (formatting.contains(Formatting.SUPERSCRIPT)) {
comment|//            xCursorProps.setPropertyValue(CHAR_ESCAPEMENT,
comment|//                    (byte) 101);
comment|//            xCursorProps.setPropertyValue(CHAR_ESCAPEMENT_HEIGHT,
comment|//                    (byte) 58);
comment|//        } else {
comment|//            xCursorProps.setPropertyValue(CHAR_ESCAPEMENT,
comment|//                    (byte) 0);
comment|//            xCursorProps.setPropertyValue(CHAR_ESCAPEMENT_HEIGHT,
comment|//                    (byte) 100);
comment|//        }
comment|//
comment|//        if (formatting.contains(Formatting.UNDERLINE)) {
comment|//            xCursorProps.setPropertyValue(CHAR_UNDERLINE, com.sun.star.awt.FontUnderline.SINGLE);
comment|//        } else {
comment|//            xCursorProps.setPropertyValue(CHAR_UNDERLINE, com.sun.star.awt.FontUnderline.NONE);
comment|//        }
comment|//
comment|//        if (formatting.contains(Formatting.STRIKEOUT)) {
comment|//            xCursorProps.setPropertyValue(CHAR_STRIKEOUT, com.sun.star.awt.FontStrikeout.SINGLE);
comment|//        } else {
comment|//            xCursorProps.setPropertyValue(CHAR_STRIKEOUT, com.sun.star.awt.FontStrikeout.NONE);
comment|//        }
comment|//        cursor.collapseToEnd();
comment|//    }
comment|//
comment|//    public static void insertTextAtCurrentLocation(XText text, XTextCursor cursor, String string, String parStyle)
comment|//            throws WrappedTargetException, PropertyVetoException, UnknownPropertyException,
comment|//            UndefinedParagraphFormatException {
comment|//        text.insertString(cursor, string, true);
comment|//        XParagraphCursor parCursor = UnoRuntime.queryInterface(
comment|//                XParagraphCursor.class, cursor);
comment|//        // Access the property set of the cursor, and set the currently selected text
comment|//        // (which is the string we just inserted) to be bold
comment|//        XPropertySet props = UnoRuntime.queryInterface(
comment|//                XPropertySet.class, parCursor);
comment|//        try {
comment|//            props.setPropertyValue(PARA_STYLE_NAME, parStyle);
comment|//        } catch (IllegalArgumentException ex) {
comment|//            throw new UndefinedParagraphFormatException(parStyle);
comment|//        }
comment|//        cursor.collapseToEnd();
comment|//
comment|//    }
comment|//
comment|//    public static Object getProperty(Object o, String property)
comment|//            throws UnknownPropertyException, WrappedTargetException {
comment|//        XPropertySet props = UnoRuntime.queryInterface(
comment|//                XPropertySet.class, o);
comment|//        return props.getPropertyValue(property);
comment|//    }
block|}
end_class

end_unit

