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
name|field
operator|.
name|Field
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
name|field
operator|.
name|UnknownField
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|beans
operator|.
name|PropertyVetoException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|beans
operator|.
name|UnknownPropertyException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|beans
operator|.
name|XPropertySet
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|lang
operator|.
name|WrappedTargetException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|text
operator|.
name|ControlCharacter
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|text
operator|.
name|XParagraphCursor
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|text
operator|.
name|XText
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|text
operator|.
name|XTextCursor
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|uno
operator|.
name|UnoRuntime
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
DECL|field|CHAR_STRIKEOUT
specifier|private
specifier|static
specifier|final
name|String
name|CHAR_STRIKEOUT
init|=
literal|"CharStrikeout"
decl_stmt|;
DECL|field|CHAR_UNDERLINE
specifier|private
specifier|static
specifier|final
name|String
name|CHAR_UNDERLINE
init|=
literal|"CharUnderline"
decl_stmt|;
DECL|field|PARA_STYLE_NAME
specifier|private
specifier|static
specifier|final
name|String
name|PARA_STYLE_NAME
init|=
literal|"ParaStyleName"
decl_stmt|;
DECL|field|CHAR_CASE_MAP
specifier|private
specifier|static
specifier|final
name|String
name|CHAR_CASE_MAP
init|=
literal|"CharCaseMap"
decl_stmt|;
DECL|field|CHAR_POSTURE
specifier|private
specifier|static
specifier|final
name|String
name|CHAR_POSTURE
init|=
literal|"CharPosture"
decl_stmt|;
DECL|field|CHAR_WEIGHT
specifier|private
specifier|static
specifier|final
name|String
name|CHAR_WEIGHT
init|=
literal|"CharWeight"
decl_stmt|;
DECL|field|CHAR_ESCAPEMENT_HEIGHT
specifier|private
specifier|static
specifier|final
name|String
name|CHAR_ESCAPEMENT_HEIGHT
init|=
literal|"CharEscapementHeight"
decl_stmt|;
DECL|field|CHAR_ESCAPEMENT
specifier|private
specifier|static
specifier|final
name|String
name|CHAR_ESCAPEMENT
init|=
literal|"CharEscapement"
decl_stmt|;
DECL|enum|Formatting
specifier|public
enum|enum
name|Formatting
block|{
DECL|enumConstant|BOLD
name|BOLD
block|,
DECL|enumConstant|ITALIC
name|ITALIC
block|,
DECL|enumConstant|SMALLCAPS
name|SMALLCAPS
block|,
DECL|enumConstant|SUPERSCRIPT
name|SUPERSCRIPT
block|,
DECL|enumConstant|SUBSCRIPT
name|SUBSCRIPT
block|,
DECL|enumConstant|UNDERLINE
name|UNDERLINE
block|,
DECL|enumConstant|STRIKEOUT
name|STRIKEOUT
block|,
DECL|enumConstant|MONOSPACE
name|MONOSPACE
block|}
DECL|field|HTML_TAG
specifier|private
specifier|static
specifier|final
name|Pattern
name|HTML_TAG
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"</?[a-z]+>"
argument_list|)
decl_stmt|;
DECL|field|UNIQUEFIER_FIELD
specifier|private
specifier|static
specifier|final
name|Field
name|UNIQUEFIER_FIELD
init|=
operator|new
name|UnknownField
argument_list|(
literal|"uniq"
argument_list|)
decl_stmt|;
DECL|method|OOUtil ()
specifier|private
name|OOUtil
parameter_list|()
block|{
comment|// Just to hide the public constructor
block|}
comment|/**      * Insert a reference, formatted using a Layout, at the position of a given cursor.      * @param text The text to insert in.      * @param cursor The cursor giving the insert location.      * @param layout The Layout to format the reference with.      * @param parStyle The name of the paragraph style to use.      * @param entry The entry to insert.      * @param database The database the entry belongs to.      * @param uniquefier Uniqiefier letter, if any, to append to the entry's year.      */
DECL|method|insertFullReferenceAtCurrentLocation (XText text, XTextCursor cursor, Layout layout, String parStyle, BibEntry entry, BibDatabase database, String uniquefier)
specifier|public
specifier|static
name|void
name|insertFullReferenceAtCurrentLocation
parameter_list|(
name|XText
name|text
parameter_list|,
name|XTextCursor
name|cursor
parameter_list|,
name|Layout
name|layout
parameter_list|,
name|String
name|parStyle
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|BibDatabase
name|database
parameter_list|,
name|String
name|uniquefier
parameter_list|)
throws|throws
name|UndefinedParagraphFormatException
throws|,
name|UnknownPropertyException
throws|,
name|PropertyVetoException
throws|,
name|WrappedTargetException
throws|,
name|IllegalArgumentException
block|{
comment|// Backup the value of the uniq field, just in case the entry already has it:
name|Optional
argument_list|<
name|String
argument_list|>
name|oldUniqVal
init|=
name|entry
operator|.
name|getField
argument_list|(
name|UNIQUEFIER_FIELD
argument_list|)
decl_stmt|;
comment|// Set the uniq field with the supplied uniquefier:
if|if
condition|(
name|uniquefier
operator|==
literal|null
condition|)
block|{
name|entry
operator|.
name|clearField
argument_list|(
name|UNIQUEFIER_FIELD
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|UNIQUEFIER_FIELD
argument_list|,
name|uniquefier
argument_list|)
expr_stmt|;
block|}
comment|// Do the layout for this entry:
name|String
name|formattedText
init|=
name|layout
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
name|database
argument_list|)
decl_stmt|;
comment|// Afterwards, reset the old value:
if|if
condition|(
name|oldUniqVal
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|UNIQUEFIER_FIELD
argument_list|,
name|oldUniqVal
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|clearField
argument_list|(
name|UNIQUEFIER_FIELD
argument_list|)
expr_stmt|;
block|}
comment|// Insert the formatted text:
name|OOUtil
operator|.
name|insertOOFormattedTextAtCurrentLocation
argument_list|(
name|text
argument_list|,
name|cursor
argument_list|,
name|formattedText
argument_list|,
name|parStyle
argument_list|)
expr_stmt|;
block|}
comment|/**      * Insert a text with formatting indicated by HTML-like tags, into a text at      * the position given by a cursor.      * @param text The text to insert in.      * @param cursor The cursor giving the insert location.      * @param lText The marked-up text to insert.      * @param parStyle The name of the paragraph style to use.      * @throws WrappedTargetException      * @throws PropertyVetoException      * @throws UnknownPropertyException      * @throws IllegalArgumentException      */
DECL|method|insertOOFormattedTextAtCurrentLocation (XText text, XTextCursor cursor, String lText, String parStyle)
specifier|public
specifier|static
name|void
name|insertOOFormattedTextAtCurrentLocation
parameter_list|(
name|XText
name|text
parameter_list|,
name|XTextCursor
name|cursor
parameter_list|,
name|String
name|lText
parameter_list|,
name|String
name|parStyle
parameter_list|)
throws|throws
name|UndefinedParagraphFormatException
throws|,
name|UnknownPropertyException
throws|,
name|PropertyVetoException
throws|,
name|WrappedTargetException
throws|,
name|IllegalArgumentException
block|{
name|XParagraphCursor
name|parCursor
init|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XParagraphCursor
operator|.
name|class
argument_list|,
name|cursor
argument_list|)
decl_stmt|;
name|XPropertySet
name|props
init|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XPropertySet
operator|.
name|class
argument_list|,
name|parCursor
argument_list|)
decl_stmt|;
try|try
block|{
name|props
operator|.
name|setPropertyValue
argument_list|(
name|PARA_STYLE_NAME
argument_list|,
name|parStyle
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|UndefinedParagraphFormatException
argument_list|(
name|parStyle
argument_list|)
throw|;
block|}
name|List
argument_list|<
name|Formatting
argument_list|>
name|formatting
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// We need to extract formatting. Use a simple regexp search iteration:
name|int
name|piv
init|=
literal|0
decl_stmt|;
name|Matcher
name|m
init|=
name|OOUtil
operator|.
name|HTML_TAG
operator|.
name|matcher
argument_list|(
name|lText
argument_list|)
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|currentSubstring
init|=
name|lText
operator|.
name|substring
argument_list|(
name|piv
argument_list|,
name|m
operator|.
name|start
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|currentSubstring
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|OOUtil
operator|.
name|insertTextAtCurrentLocation
argument_list|(
name|text
argument_list|,
name|cursor
argument_list|,
name|currentSubstring
argument_list|,
name|formatting
argument_list|)
expr_stmt|;
block|}
name|String
name|tag
init|=
name|m
operator|.
name|group
argument_list|()
decl_stmt|;
comment|// Handle tags:
if|if
condition|(
literal|"<b>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|add
argument_list|(
name|Formatting
operator|.
name|BOLD
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"</b>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|remove
argument_list|(
name|Formatting
operator|.
name|BOLD
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"<i>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"<em>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|add
argument_list|(
name|Formatting
operator|.
name|ITALIC
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"</i>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"</em>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|remove
argument_list|(
name|Formatting
operator|.
name|ITALIC
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"<tt>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|add
argument_list|(
name|Formatting
operator|.
name|MONOSPACE
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"</tt>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|remove
argument_list|(
name|Formatting
operator|.
name|MONOSPACE
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"<smallcaps>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|add
argument_list|(
name|Formatting
operator|.
name|SMALLCAPS
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"</smallcaps>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|remove
argument_list|(
name|Formatting
operator|.
name|SMALLCAPS
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"<sup>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|add
argument_list|(
name|Formatting
operator|.
name|SUPERSCRIPT
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"</sup>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|remove
argument_list|(
name|Formatting
operator|.
name|SUPERSCRIPT
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"<sub>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|add
argument_list|(
name|Formatting
operator|.
name|SUBSCRIPT
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"</sub>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|remove
argument_list|(
name|Formatting
operator|.
name|SUBSCRIPT
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"<u>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|add
argument_list|(
name|Formatting
operator|.
name|UNDERLINE
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"</u>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|remove
argument_list|(
name|Formatting
operator|.
name|UNDERLINE
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"<s>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|add
argument_list|(
name|Formatting
operator|.
name|STRIKEOUT
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"</s>"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|formatting
operator|.
name|remove
argument_list|(
name|Formatting
operator|.
name|STRIKEOUT
argument_list|)
expr_stmt|;
block|}
name|piv
operator|=
name|m
operator|.
name|end
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|piv
operator|<
name|lText
operator|.
name|length
argument_list|()
condition|)
block|{
name|OOUtil
operator|.
name|insertTextAtCurrentLocation
argument_list|(
name|text
argument_list|,
name|cursor
argument_list|,
name|lText
operator|.
name|substring
argument_list|(
name|piv
argument_list|)
argument_list|,
name|formatting
argument_list|)
expr_stmt|;
block|}
name|cursor
operator|.
name|collapseToEnd
argument_list|()
expr_stmt|;
block|}
DECL|method|insertParagraphBreak (XText text, XTextCursor cursor)
specifier|public
specifier|static
name|void
name|insertParagraphBreak
parameter_list|(
name|XText
name|text
parameter_list|,
name|XTextCursor
name|cursor
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|text
operator|.
name|insertControlCharacter
argument_list|(
name|cursor
argument_list|,
name|ControlCharacter
operator|.
name|PARAGRAPH_BREAK
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|cursor
operator|.
name|collapseToEnd
argument_list|()
expr_stmt|;
block|}
DECL|method|insertTextAtCurrentLocation (XText text, XTextCursor cursor, String string, List<Formatting> formatting)
specifier|public
specifier|static
name|void
name|insertTextAtCurrentLocation
parameter_list|(
name|XText
name|text
parameter_list|,
name|XTextCursor
name|cursor
parameter_list|,
name|String
name|string
parameter_list|,
name|List
argument_list|<
name|Formatting
argument_list|>
name|formatting
parameter_list|)
throws|throws
name|UnknownPropertyException
throws|,
name|PropertyVetoException
throws|,
name|WrappedTargetException
throws|,
name|IllegalArgumentException
block|{
name|text
operator|.
name|insertString
argument_list|(
name|cursor
argument_list|,
name|string
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|// Access the property set of the cursor, and set the currently selected text
comment|// (which is the string we just inserted) to be bold
name|XPropertySet
name|xCursorProps
init|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XPropertySet
operator|.
name|class
argument_list|,
name|cursor
argument_list|)
decl_stmt|;
if|if
condition|(
name|formatting
operator|.
name|contains
argument_list|(
name|Formatting
operator|.
name|BOLD
argument_list|)
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_WEIGHT
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|FontWeight
operator|.
name|BOLD
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_WEIGHT
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|FontWeight
operator|.
name|NORMAL
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|formatting
operator|.
name|contains
argument_list|(
name|Formatting
operator|.
name|ITALIC
argument_list|)
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_POSTURE
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|FontSlant
operator|.
name|ITALIC
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_POSTURE
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|FontSlant
operator|.
name|NONE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|formatting
operator|.
name|contains
argument_list|(
name|Formatting
operator|.
name|SMALLCAPS
argument_list|)
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_CASE_MAP
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|style
operator|.
name|CaseMap
operator|.
name|SMALLCAPS
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_CASE_MAP
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|style
operator|.
name|CaseMap
operator|.
name|NONE
argument_list|)
expr_stmt|;
block|}
comment|// TODO: the<monospace> tag doesn't work
comment|/*         if (formatting.contains(Formatting.MONOSPACE)) {             xCursorProps.setPropertyValue("CharFontPitch",                             com.sun.star.awt.FontPitch.FIXED);         }         else {             xCursorProps.setPropertyValue("CharFontPitch",                             com.sun.star.awt.FontPitch.VARIABLE);         } */
if|if
condition|(
name|formatting
operator|.
name|contains
argument_list|(
name|Formatting
operator|.
name|SUBSCRIPT
argument_list|)
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_ESCAPEMENT
argument_list|,
operator|(
name|byte
operator|)
operator|-
literal|101
argument_list|)
expr_stmt|;
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_ESCAPEMENT_HEIGHT
argument_list|,
operator|(
name|byte
operator|)
literal|58
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|formatting
operator|.
name|contains
argument_list|(
name|Formatting
operator|.
name|SUPERSCRIPT
argument_list|)
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_ESCAPEMENT
argument_list|,
operator|(
name|byte
operator|)
literal|101
argument_list|)
expr_stmt|;
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_ESCAPEMENT_HEIGHT
argument_list|,
operator|(
name|byte
operator|)
literal|58
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_ESCAPEMENT
argument_list|,
operator|(
name|byte
operator|)
literal|0
argument_list|)
expr_stmt|;
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_ESCAPEMENT_HEIGHT
argument_list|,
operator|(
name|byte
operator|)
literal|100
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|formatting
operator|.
name|contains
argument_list|(
name|Formatting
operator|.
name|UNDERLINE
argument_list|)
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_UNDERLINE
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|FontUnderline
operator|.
name|SINGLE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_UNDERLINE
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|FontUnderline
operator|.
name|NONE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|formatting
operator|.
name|contains
argument_list|(
name|Formatting
operator|.
name|STRIKEOUT
argument_list|)
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_STRIKEOUT
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|FontStrikeout
operator|.
name|SINGLE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
name|CHAR_STRIKEOUT
argument_list|,
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|FontStrikeout
operator|.
name|NONE
argument_list|)
expr_stmt|;
block|}
name|cursor
operator|.
name|collapseToEnd
argument_list|()
expr_stmt|;
block|}
DECL|method|insertTextAtCurrentLocation (XText text, XTextCursor cursor, String string, String parStyle)
specifier|public
specifier|static
name|void
name|insertTextAtCurrentLocation
parameter_list|(
name|XText
name|text
parameter_list|,
name|XTextCursor
name|cursor
parameter_list|,
name|String
name|string
parameter_list|,
name|String
name|parStyle
parameter_list|)
throws|throws
name|WrappedTargetException
throws|,
name|PropertyVetoException
throws|,
name|UnknownPropertyException
throws|,
name|UndefinedParagraphFormatException
block|{
name|text
operator|.
name|insertString
argument_list|(
name|cursor
argument_list|,
name|string
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|XParagraphCursor
name|parCursor
init|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XParagraphCursor
operator|.
name|class
argument_list|,
name|cursor
argument_list|)
decl_stmt|;
comment|// Access the property set of the cursor, and set the currently selected text
comment|// (which is the string we just inserted) to be bold
name|XPropertySet
name|props
init|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XPropertySet
operator|.
name|class
argument_list|,
name|parCursor
argument_list|)
decl_stmt|;
try|try
block|{
name|props
operator|.
name|setPropertyValue
argument_list|(
name|PARA_STYLE_NAME
argument_list|,
name|parStyle
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|UndefinedParagraphFormatException
argument_list|(
name|parStyle
argument_list|)
throw|;
block|}
name|cursor
operator|.
name|collapseToEnd
argument_list|()
expr_stmt|;
block|}
DECL|method|getProperty (Object o, String property)
specifier|public
specifier|static
name|Object
name|getProperty
parameter_list|(
name|Object
name|o
parameter_list|,
name|String
name|property
parameter_list|)
throws|throws
name|UnknownPropertyException
throws|,
name|WrappedTargetException
block|{
name|XPropertySet
name|props
init|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XPropertySet
operator|.
name|class
argument_list|,
name|o
argument_list|)
decl_stmt|;
return|return
name|props
operator|.
name|getPropertyValue
argument_list|(
name|property
argument_list|)
return|;
block|}
block|}
end_class

end_unit

