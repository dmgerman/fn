begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
package|;
end_package

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
name|beans
operator|.
name|Property
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
name|*
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

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|frame
operator|.
name|XDesktop
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexDatabase
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexFields
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|Layout
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
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
name|Iterator
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
DECL|field|htmlTag
specifier|static
name|Pattern
name|htmlTag
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"</?[a-z]+>"
argument_list|)
decl_stmt|;
DECL|field|postformatter
specifier|static
name|OOPreFormatter
name|postformatter
init|=
operator|new
name|OOPreFormatter
argument_list|()
decl_stmt|;
comment|/**      * Insert a reference, formatted using a Layout, at the position of a given cursor.      * @param text The text to insert in.      * @param cursor The cursor giving the insert location.      * @param layout The Layout to format the reference with.      * @param parStyle The name of the paragraph style to use.      * @param entry The entry to insert.      * @param database The database the entry belongs to.      * @param uniquefier Uniqiefier letter, if any, to append to the entry's year.      * @throws Exception      */
DECL|method|insertFullReferenceAtCurrentLocation (XText text, XTextCursor cursor, Layout layout, String parStyle, BibtexEntry entry, BibtexDatabase database, String uniquefier)
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
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|,
name|String
name|uniquefier
parameter_list|)
throws|throws
name|UndefinedParagraphFormatException
throws|,
name|Exception
block|{
specifier|final
name|String
name|UNIQUEFIER_FIELD
init|=
literal|"uniq"
decl_stmt|;
comment|// Backup the value of the uniq field, just in case the entry already has it:
name|String
name|oldUniqVal
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getField
argument_list|(
name|UNIQUEFIER_FIELD
argument_list|)
decl_stmt|;
comment|// Set the uniq field with the supplied uniquefier:
name|entry
operator|.
name|setField
argument_list|(
name|UNIQUEFIER_FIELD
argument_list|,
name|uniquefier
argument_list|)
expr_stmt|;
comment|// Do the layout for this entry:
name|String
name|lText
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
name|entry
operator|.
name|setField
argument_list|(
name|UNIQUEFIER_FIELD
argument_list|,
name|oldUniqVal
argument_list|)
expr_stmt|;
comment|// Insert the formatted text:
name|insertOOFormattedTextAtCurrentLocation
argument_list|(
name|text
argument_list|,
name|cursor
argument_list|,
name|lText
argument_list|,
name|parStyle
argument_list|)
expr_stmt|;
block|}
comment|/**      * Insert a text with formatting indicated by HTML-like tags, into a text at          * the position given by a cursor.      * @param text The text to insert in.      * @param cursor The cursor giving the insert location.      * @param lText The marked-up text to insert.      * @param parStyle The name of the paragraph style to use.      * @throws Exception      */
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
name|Exception
block|{
name|XParagraphCursor
name|parCursor
init|=
operator|(
name|XParagraphCursor
operator|)
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
operator|(
name|XPropertySet
operator|)
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
literal|"ParaStyleName"
argument_list|,
name|parStyle
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|lang
operator|.
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
comment|// We need to extract formatting. Use a simple regexp search iteration:
name|int
name|piv
init|=
literal|0
decl_stmt|;
name|int
name|italic
init|=
literal|0
decl_stmt|,
name|bold
init|=
literal|0
decl_stmt|,
name|sup
init|=
literal|0
decl_stmt|,
name|sub
init|=
literal|0
decl_stmt|,
name|mono
init|=
literal|0
decl_stmt|,
name|smallCaps
init|=
literal|0
decl_stmt|;
comment|//insertTextAtCurrentLocation(text, cursor, "_",
comment|//    false, false, false, false, false, false);
comment|//cursor.goLeft((short)1, true);
name|Matcher
name|m
init|=
name|htmlTag
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
name|ss
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
name|ss
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|insertTextAtCurrentLocation
argument_list|(
name|text
argument_list|,
name|cursor
argument_list|,
name|ss
argument_list|,
operator|(
name|bold
operator|%
literal|2
operator|)
operator|>
literal|0
argument_list|,
operator|(
name|italic
operator|%
literal|2
operator|)
operator|>
literal|0
argument_list|,
name|mono
operator|>
literal|0
argument_list|,
name|smallCaps
operator|>
literal|0
argument_list|,
name|sup
operator|>
literal|0
argument_list|,
name|sub
operator|>
literal|0
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
name|tag
operator|.
name|equals
argument_list|(
literal|"<b>"
argument_list|)
condition|)
name|bold
operator|++
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"</b>"
argument_list|)
condition|)
name|bold
operator|--
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"<i>"
argument_list|)
operator|||
name|tag
operator|.
name|equals
argument_list|(
literal|"<em>"
argument_list|)
condition|)
name|italic
operator|++
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"</i>"
argument_list|)
operator|||
name|tag
operator|.
name|equals
argument_list|(
literal|"</em>"
argument_list|)
condition|)
name|italic
operator|--
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"</monospace>"
argument_list|)
condition|)
name|mono
operator|=
literal|0
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"<monospace>"
argument_list|)
condition|)
name|mono
operator|=
literal|1
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"</smallcaps>"
argument_list|)
condition|)
name|smallCaps
operator|=
literal|0
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"<smallcaps>"
argument_list|)
condition|)
name|smallCaps
operator|=
literal|1
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"</sup>"
argument_list|)
condition|)
name|sup
operator|=
literal|0
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"<sup>"
argument_list|)
condition|)
name|sup
operator|=
literal|1
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"</sub>"
argument_list|)
condition|)
name|sub
operator|=
literal|0
expr_stmt|;
elseif|else
if|if
condition|(
name|tag
operator|.
name|equals
argument_list|(
literal|"<sub>"
argument_list|)
condition|)
name|sub
operator|=
literal|1
expr_stmt|;
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
operator|(
name|bold
operator|%
literal|2
operator|)
operator|>
literal|0
argument_list|,
operator|(
name|italic
operator|%
literal|2
operator|)
operator|>
literal|0
argument_list|,
name|mono
operator|>
literal|0
argument_list|,
name|smallCaps
operator|>
literal|0
argument_list|,
name|sup
operator|>
literal|0
argument_list|,
name|sub
operator|>
literal|0
argument_list|)
expr_stmt|;
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
name|Exception
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
DECL|method|insertTextAtCurrentLocation (XText text, XTextCursor cursor, String string, boolean bold, boolean italic, boolean monospace, boolean smallCaps, boolean superscript, boolean subscript)
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
name|boolean
name|bold
parameter_list|,
name|boolean
name|italic
parameter_list|,
name|boolean
name|monospace
parameter_list|,
name|boolean
name|smallCaps
parameter_list|,
name|boolean
name|superscript
parameter_list|,
name|boolean
name|subscript
parameter_list|)
throws|throws
name|Exception
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
operator|(
name|XPropertySet
operator|)
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
name|bold
condition|)
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
literal|"CharWeight"
argument_list|,
operator|new
name|Float
argument_list|(
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
argument_list|)
expr_stmt|;
else|else
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
literal|"CharWeight"
argument_list|,
operator|new
name|Float
argument_list|(
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
argument_list|)
expr_stmt|;
if|if
condition|(
name|italic
condition|)
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
literal|"CharPosture"
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
else|else
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
literal|"CharPosture"
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
if|if
condition|(
name|smallCaps
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
literal|"CharCaseMap"
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
literal|"CharCaseMap"
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
comment|/*         if (monospace) {             xCursorProps.setPropertyValue("CharFontPitch",                             com.sun.star.awt.FontPitch.FIXED);         }         else {             xCursorProps.setPropertyValue("CharFontPitch",                             com.sun.star.awt.FontPitch.VARIABLE);         } */
if|if
condition|(
name|subscript
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
literal|"CharEscapement"
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
literal|"CharEscapementHeight"
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
name|superscript
condition|)
block|{
name|xCursorProps
operator|.
name|setPropertyValue
argument_list|(
literal|"CharEscapement"
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
literal|"CharEscapementHeight"
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
literal|"CharEscapement"
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
literal|"CharEscapementHeight"
argument_list|,
operator|(
name|byte
operator|)
literal|100
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
name|Exception
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
operator|(
name|XParagraphCursor
operator|)
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
operator|(
name|XPropertySet
operator|)
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
literal|"ParaStyleName"
argument_list|,
name|parStyle
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|lang
operator|.
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
name|Exception
block|{
name|XPropertySet
name|props
init|=
operator|(
name|XPropertySet
operator|)
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
DECL|method|listProperties (Object o)
specifier|public
specifier|static
name|void
name|listProperties
parameter_list|(
name|Object
name|o
parameter_list|)
throws|throws
name|Exception
block|{
name|XPropertySet
name|props
init|=
operator|(
name|XPropertySet
operator|)
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
name|Property
index|[]
name|pr
init|=
name|props
operator|.
name|getPropertySetInfo
argument_list|()
operator|.
name|getProperties
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|pr
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Property
name|property1
init|=
name|pr
index|[
name|i
index|]
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|property1
operator|.
name|Name
operator|+
literal|" : "
operator|+
name|props
operator|.
name|getPropertyValue
argument_list|(
name|property1
operator|.
name|Name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|selectComponent (JFrame parent, XDesktop xDesktop, List<XTextDocument> list)
specifier|public
specifier|static
name|XTextDocument
name|selectComponent
parameter_list|(
name|JFrame
name|parent
parameter_list|,
name|XDesktop
name|xDesktop
parameter_list|,
name|List
argument_list|<
name|XTextDocument
argument_list|>
name|list
parameter_list|)
throws|throws
name|Exception
block|{
name|String
index|[]
name|values
init|=
operator|new
name|String
index|[
name|list
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|int
name|ii
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|XTextDocument
argument_list|>
name|iterator
init|=
name|list
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|XTextDocument
name|doc
init|=
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|values
index|[
name|ii
operator|++
index|]
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|getProperty
argument_list|(
name|doc
operator|.
name|getCurrentController
argument_list|()
operator|.
name|getFrame
argument_list|()
argument_list|,
literal|"Title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|JList
name|sel
init|=
operator|new
name|JList
argument_list|(
name|values
argument_list|)
decl_stmt|;
name|sel
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|sel
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|int
name|ans
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|parent
argument_list|,
operator|new
name|JScrollPane
argument_list|(
name|sel
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Select document"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|ans
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
return|return
name|list
operator|.
name|get
argument_list|(
name|sel
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
return|;
block|}
else|else
return|return
literal|null
return|;
block|}
comment|/**      * Make a cloned BibtexEntry and do the necessary preprocessing for use by the plugin.      * If the running JabRef version doesn't support post-processing in Layout, this      * preprocessing includes running the OOPreFormatter formatter for all fields except the      * BibTeX key.      * @param entry the original entry      * @return the cloned and processed entry      */
DECL|method|createAdaptedEntry (BibtexEntry entry)
specifier|public
specifier|static
name|BibtexEntry
name|createAdaptedEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|BibtexEntry
name|e
init|=
operator|(
name|BibtexEntry
operator|)
name|entry
operator|.
name|clone
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|e
operator|.
name|getAllFields
argument_list|()
control|)
block|{
if|if
condition|(
name|field
operator|.
name|equals
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
condition|)
continue|continue;
name|String
name|value
init|=
name|e
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|// If the running JabRef version doesn't support post-processing in Layout,
comment|// preprocess fields instead:
if|if
condition|(
operator|!
name|OpenOfficePanel
operator|.
name|postLayoutSupported
operator|&&
operator|(
name|value
operator|!=
literal|null
operator|)
condition|)
name|e
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|postformatter
operator|.
name|format
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|e
return|;
block|}
block|}
end_class

end_unit

