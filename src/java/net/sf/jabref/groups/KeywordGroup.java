begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
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
name|Pattern
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
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
name|*
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
name|undo
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * @author zieren  *   * TODO To change the template for this generated type comment go to Window -  * Preferences - Java - Code Style - Code Templates  */
end_comment

begin_class
DECL|class|KeywordGroup
specifier|public
class|class
name|KeywordGroup
extends|extends
name|AbstractGroup
implements|implements
name|SearchRule
block|{
DECL|field|ID
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"KeywordGroup:"
decl_stmt|;
DECL|field|m_name
specifier|private
name|String
name|m_name
decl_stmt|;
DECL|field|m_searchField
specifier|private
name|String
name|m_searchField
decl_stmt|;
DECL|field|m_searchExpression
specifier|private
name|String
name|m_searchExpression
decl_stmt|;
DECL|field|m_pattern
specifier|private
name|Pattern
name|m_pattern
decl_stmt|;
DECL|field|m_expressionMatchesItself
specifier|private
name|boolean
name|m_expressionMatchesItself
init|=
literal|false
decl_stmt|;
comment|/**      * Creates a KeywordGroup with the specified properties.      */
DECL|method|KeywordGroup (String name, String searchField, String searchExpression)
specifier|public
name|KeywordGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|searchField
parameter_list|,
name|String
name|searchExpression
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|PatternSyntaxException
block|{
name|m_name
operator|=
name|name
expr_stmt|;
name|m_searchField
operator|=
name|searchField
expr_stmt|;
name|m_searchExpression
operator|=
name|searchExpression
expr_stmt|;
name|compilePattern
argument_list|()
expr_stmt|;
block|}
DECL|method|compilePattern ()
specifier|private
name|void
name|compilePattern
parameter_list|()
throws|throws
name|IllegalArgumentException
throws|,
name|PatternSyntaxException
block|{
name|m_pattern
operator|=
name|Pattern
operator|.
name|compile
argument_list|(
name|m_searchExpression
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
expr_stmt|;
comment|// this is required to decide whether entries can be added
comment|// to this group by adding m_searchExpression to the m_searchField
comment|// (it's quite a hack, but the only solution would be to disable
comment|// add/remove completely for keyword groups)
name|m_expressionMatchesItself
operator|=
name|m_pattern
operator|.
name|matcher
argument_list|(
name|m_searchExpression
argument_list|)
operator|.
name|matches
argument_list|()
expr_stmt|;
block|}
comment|/**      * Parses s and recreates the KeywordGroup from it.      *       * @param s      *            The String representation obtained from      *            KeywordGroup.toString()      */
DECL|method|fromString (String s)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|startsWith
argument_list|(
name|ID
argument_list|)
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Internal error: KeywordGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\""
argument_list|)
throw|;
name|QuotedStringTokenizer
name|tok
init|=
operator|new
name|QuotedStringTokenizer
argument_list|(
name|s
operator|.
name|substring
argument_list|(
name|ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|String
name|field
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|String
name|expression
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
return|return
operator|new
name|KeywordGroup
argument_list|(
name|Util
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|Util
operator|.
name|unquote
argument_list|(
name|field
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|Util
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * @see net.sf.jabref.groups.AbstractGroup#getSearchRule()      */
DECL|method|getSearchRule ()
specifier|public
name|SearchRule
name|getSearchRule
parameter_list|()
block|{
return|return
name|this
return|;
block|}
comment|/**      * Returns a String representation of this object that can be used to      * reconstruct it.      */
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|ID
operator|+
name|Util
operator|.
name|quote
argument_list|(
name|m_name
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
operator|+
name|SEPARATOR
operator|+
name|Util
operator|.
name|quote
argument_list|(
name|m_searchField
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
operator|+
name|SEPARATOR
operator|+
name|Util
operator|.
name|quote
argument_list|(
name|m_searchExpression
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
operator|+
name|SEPARATOR
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|m_name
return|;
block|}
DECL|method|setName (String m_name)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|m_name
parameter_list|)
block|{
name|this
operator|.
name|m_name
operator|=
name|m_name
expr_stmt|;
block|}
DECL|method|getSearchField ()
specifier|public
name|String
name|getSearchField
parameter_list|()
block|{
return|return
name|m_searchField
return|;
block|}
DECL|method|setSearchField (String field)
specifier|public
name|void
name|setSearchField
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|m_searchField
operator|=
name|field
expr_stmt|;
block|}
DECL|method|getSearchExpression ()
specifier|public
name|String
name|getSearchExpression
parameter_list|()
block|{
return|return
name|m_searchExpression
return|;
block|}
DECL|method|setSearchExpression (String expression)
specifier|public
name|void
name|setSearchExpression
parameter_list|(
name|String
name|expression
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|PatternSyntaxException
block|{
name|m_searchExpression
operator|=
name|expression
expr_stmt|;
name|compilePattern
argument_list|()
expr_stmt|;
block|}
DECL|method|supportsAdd ()
specifier|public
name|boolean
name|supportsAdd
parameter_list|()
block|{
return|return
name|m_expressionMatchesItself
return|;
block|}
DECL|method|supportsRemove ()
specifier|public
name|boolean
name|supportsRemove
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
DECL|method|addSelection (BasePanel basePanel)
specifier|public
name|void
name|addSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
if|if
condition|(
operator|!
name|supportsAdd
argument_list|()
condition|)
block|{
comment|// this should never happen
name|basePanel
operator|.
name|output
argument_list|(
literal|"The group \""
operator|+
name|getName
argument_list|()
operator|+
literal|"\" does not support the adding of entries."
argument_list|)
expr_stmt|;
return|return;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|GUIGlobals
operator|.
name|ALL_FIELDS
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
if|if
condition|(
name|m_searchField
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|ALL_FIELDS
index|[
name|i
index|]
argument_list|)
operator|&&
operator|!
name|m_searchField
operator|.
name|equals
argument_list|(
literal|"keywords"
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|showWarningDialog
argument_list|(
name|basePanel
argument_list|)
condition|)
return|return;
block|}
block|}
name|BibtexEntry
index|[]
name|bes
init|=
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|bes
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|bes
operator|.
name|length
operator|>
literal|0
operator|)
condition|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"add to group"
argument_list|)
decl_stmt|;
name|boolean
name|hasEdits
init|=
literal|false
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
name|bes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|applyRule
argument_list|(
literal|null
argument_list|,
name|bes
index|[
name|i
index|]
argument_list|)
operator|==
literal|0
condition|)
block|{
name|String
name|oldContent
init|=
operator|(
name|String
operator|)
name|bes
index|[
name|i
index|]
operator|.
name|getField
argument_list|(
name|m_searchField
argument_list|)
decl_stmt|,
name|pre
init|=
literal|" "
decl_stmt|,
name|post
init|=
literal|""
decl_stmt|;
name|String
name|newContent
init|=
operator|(
name|oldContent
operator|==
literal|null
condition|?
literal|""
else|:
name|oldContent
operator|+
name|pre
operator|)
operator|+
name|m_searchExpression
operator|+
name|post
decl_stmt|;
name|bes
index|[
name|i
index|]
operator|.
name|setField
argument_list|(
name|m_searchField
argument_list|,
name|newContent
argument_list|)
expr_stmt|;
comment|// Store undo information.
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|bes
index|[
name|i
index|]
argument_list|,
name|m_searchField
argument_list|,
name|oldContent
argument_list|,
name|newContent
argument_list|)
argument_list|)
expr_stmt|;
name|hasEdits
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|hasEdits
condition|)
block|{
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|refreshTable
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|updateViewToSelected
argument_list|()
expr_stmt|;
block|}
name|basePanel
operator|.
name|output
argument_list|(
literal|"Appended '"
operator|+
name|m_searchExpression
operator|+
literal|"' to the '"
operator|+
name|m_searchField
operator|+
literal|"' field of "
operator|+
name|bes
operator|.
name|length
operator|+
literal|" entr"
operator|+
operator|(
name|bes
operator|.
name|length
operator|>
literal|1
condition|?
literal|"ies."
else|:
literal|"y."
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Displays a warning message about changes to the entries due to adding      * to/removal from a group.      *       * @return true if the user chose to proceed, false otherwise.      */
DECL|method|showWarningDialog (BasePanel basePanel)
specifier|private
name|boolean
name|showWarningDialog
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|String
name|message
init|=
literal|"This action will modify the \""
operator|+
name|m_searchField
operator|+
literal|"\" field "
operator|+
literal|"of your entries.\nThis could cause undesired changes to "
operator|+
literal|"your entries, so it\nis recommended that you change the field "
operator|+
literal|"in your group\ndefinition to \"keywords\" or a non-standard name."
operator|+
literal|"\n\nDo you still want to continue?"
decl_stmt|;
name|int
name|choice
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|basePanel
argument_list|,
name|message
argument_list|,
literal|"Warning"
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
decl_stmt|;
return|return
name|choice
operator|!=
name|JOptionPane
operator|.
name|NO_OPTION
return|;
block|}
DECL|method|removeSelection (BasePanel basePanel)
specifier|public
name|void
name|removeSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
if|if
condition|(
operator|!
name|supportsRemove
argument_list|()
condition|)
block|{
comment|// this should never happen
name|basePanel
operator|.
name|output
argument_list|(
literal|"The group \""
operator|+
name|getName
argument_list|()
operator|+
literal|"\" does not support the removal of entries."
argument_list|)
expr_stmt|;
return|return;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|GUIGlobals
operator|.
name|ALL_FIELDS
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|m_searchField
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|ALL_FIELDS
index|[
name|i
index|]
argument_list|)
operator|&&
operator|!
name|m_searchField
operator|.
name|equals
argument_list|(
literal|"keywords"
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|showWarningDialog
argument_list|(
name|basePanel
argument_list|)
condition|)
return|return;
block|}
block|}
name|BibtexEntry
index|[]
name|selectedEntries
init|=
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|selectedEntries
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|selectedEntries
operator|.
name|length
operator|>
literal|0
operator|)
condition|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"remove from group"
argument_list|)
decl_stmt|;
name|boolean
name|hasEdits
init|=
literal|false
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
name|selectedEntries
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
if|if
condition|(
name|applyRule
argument_list|(
literal|null
argument_list|,
name|selectedEntries
index|[
name|i
index|]
argument_list|)
operator|>
literal|0
condition|)
block|{
name|String
name|oldContent
init|=
operator|(
name|String
operator|)
name|selectedEntries
index|[
name|i
index|]
operator|.
name|getField
argument_list|(
name|m_searchField
argument_list|)
decl_stmt|;
name|removeMatches
argument_list|(
name|selectedEntries
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|// Store undo information.
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|selectedEntries
index|[
name|i
index|]
argument_list|,
name|m_searchField
argument_list|,
name|oldContent
argument_list|,
name|selectedEntries
index|[
name|i
index|]
operator|.
name|getField
argument_list|(
name|m_searchField
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|hasEdits
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|hasEdits
condition|)
block|{
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|refreshTable
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|updateViewToSelected
argument_list|()
expr_stmt|;
block|}
name|basePanel
operator|.
name|output
argument_list|(
literal|"Removed '"
operator|+
name|m_searchExpression
operator|+
literal|"' from the '"
operator|+
name|m_searchField
operator|+
literal|"' field of "
operator|+
name|selectedEntries
operator|.
name|length
operator|+
literal|" entr"
operator|+
operator|(
name|selectedEntries
operator|.
name|length
operator|>
literal|1
condition|?
literal|"ies."
else|:
literal|"y."
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|o
operator|instanceof
name|KeywordGroup
operator|)
condition|)
return|return
literal|false
return|;
name|KeywordGroup
name|other
init|=
operator|(
name|KeywordGroup
operator|)
name|o
decl_stmt|;
return|return
name|m_name
operator|.
name|equals
argument_list|(
name|other
operator|.
name|m_name
argument_list|)
operator|&&
name|m_searchField
operator|.
name|equals
argument_list|(
name|other
operator|.
name|m_searchField
argument_list|)
operator|&&
name|m_searchExpression
operator|.
name|equals
argument_list|(
name|other
operator|.
name|m_searchExpression
argument_list|)
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @see net.sf.jabref.groups.AbstractGroup#contains(java.util.Map,      *      net.sf.jabref.BibtexEntry)      */
DECL|method|contains (Map searchOptions, BibtexEntry entry)
specifier|public
name|int
name|contains
parameter_list|(
name|Map
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|String
name|content
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getField
argument_list|(
name|m_searchField
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|content
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|m_pattern
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
operator|.
name|find
argument_list|()
operator|)
condition|)
return|return
literal|1
return|;
return|return
literal|0
return|;
block|}
comment|/**      * Removes matches of searchString in the entry's field.      */
DECL|method|removeMatches (BibtexEntry entry)
specifier|private
name|void
name|removeMatches
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|String
name|content
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getField
argument_list|(
name|m_searchField
argument_list|)
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|content
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|split
init|=
name|m_pattern
operator|.
name|split
argument_list|(
name|content
argument_list|)
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
name|split
operator|.
name|length
condition|;
operator|++
name|i
control|)
name|sb
operator|.
name|append
argument_list|(
name|split
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|entry
operator|.
name|setField
argument_list|(
name|m_searchField
argument_list|,
operator|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|?
name|sb
operator|.
name|toString
argument_list|()
else|:
literal|null
operator|)
argument_list|)
expr_stmt|;
block|}
DECL|method|applyRule (Map searchOptions, BibtexEntry entry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|contains
argument_list|(
name|searchOptions
argument_list|,
name|entry
argument_list|)
return|;
block|}
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
try|try
block|{
return|return
operator|new
name|KeywordGroup
argument_list|(
name|m_name
argument_list|,
name|m_searchField
argument_list|,
name|m_searchExpression
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|// this should never happen, because the constructor obviously
comment|// succeeded in creating _this_ instance!
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Internal error: Exception "
operator|+
name|t
operator|+
literal|" in KeywordGroup.deepCopy()"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

