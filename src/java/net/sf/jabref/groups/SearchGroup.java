begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* All programs in this directory and subdirectories are published under the  GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it  under the terms of the GNU General Public License as published by the Free  Software Foundation; either version 2 of the License, or (at your option)  any later version.  This program is distributed in the hope that it will be useful, but WITHOUT  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for  more details.  You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc., 59  Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html */
end_comment

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
name|io
operator|.
name|StringReader
import|;
end_import

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
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|AbstractUndoableEdit
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
name|search
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
name|util
operator|.
name|QuotedStringTokenizer
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|RecognitionException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|AST
import|;
end_import

begin_comment
comment|/**  * @author jzieren  *   * TODO To change the template for this generated type comment go to Window -  * Preferences - Java - Code Style - Code Templates  */
end_comment

begin_class
DECL|class|SearchGroup
specifier|public
class|class
name|SearchGroup
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
literal|"SearchGroup:"
decl_stmt|;
DECL|field|m_searchExpression
specifier|private
specifier|final
name|String
name|m_searchExpression
decl_stmt|;
DECL|field|m_caseSensitive
specifier|private
specifier|final
name|boolean
name|m_caseSensitive
decl_stmt|;
DECL|field|m_regExp
specifier|private
specifier|final
name|boolean
name|m_regExp
decl_stmt|;
DECL|field|m_ast
specifier|private
specifier|final
name|AST
name|m_ast
decl_stmt|;
DECL|field|m_treeParser
specifier|private
specifier|static
specifier|final
name|SearchExpressionTreeParser
name|m_treeParser
init|=
operator|new
name|SearchExpressionTreeParser
argument_list|()
decl_stmt|;
comment|/**      * If m_searchExpression is in valid syntax for advanced search,<b>this      *</b> will do the search; otherwise, either<b>RegExpRule</b> or      *<b>SimpleSearchRule</b> will be used.      */
DECL|field|m_searchRule
specifier|private
specifier|final
name|SearchRule
name|m_searchRule
decl_stmt|;
comment|// Options for non-advanced search
DECL|field|m_searchAllFields
specifier|private
specifier|final
name|boolean
name|m_searchAllFields
decl_stmt|;
DECL|field|m_searchRequiredFields
specifier|private
specifier|final
name|boolean
name|m_searchRequiredFields
decl_stmt|;
DECL|field|m_searchOptionalFields
specifier|private
specifier|final
name|boolean
name|m_searchOptionalFields
decl_stmt|;
DECL|field|m_searchGeneralFields
specifier|private
specifier|final
name|boolean
name|m_searchGeneralFields
decl_stmt|;
comment|/**      * Creates a SearchGroup with the specified properties.      */
DECL|method|SearchGroup (String name, String searchExpression, boolean caseSensitive, boolean regExp, boolean searchAllFields, boolean searchRequiredFields, boolean searchOptionalFields, boolean searchGeneralFields)
specifier|public
name|SearchGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|searchExpression
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|,
name|boolean
name|searchAllFields
parameter_list|,
name|boolean
name|searchRequiredFields
parameter_list|,
name|boolean
name|searchOptionalFields
parameter_list|,
name|boolean
name|searchGeneralFields
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|m_searchExpression
operator|=
name|searchExpression
expr_stmt|;
name|m_caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|m_regExp
operator|=
name|regExp
expr_stmt|;
name|m_searchAllFields
operator|=
name|searchAllFields
expr_stmt|;
name|m_searchRequiredFields
operator|=
name|searchRequiredFields
expr_stmt|;
name|m_searchOptionalFields
operator|=
name|searchOptionalFields
expr_stmt|;
name|m_searchGeneralFields
operator|=
name|searchGeneralFields
expr_stmt|;
comment|// create AST
name|AST
name|ast
init|=
literal|null
decl_stmt|;
try|try
block|{
name|SearchExpressionParser
name|parser
init|=
operator|new
name|SearchExpressionParser
argument_list|(
operator|new
name|SearchExpressionLexer
argument_list|(
operator|new
name|StringReader
argument_list|(
name|m_searchExpression
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|parser
operator|.
name|caseSensitive
operator|=
name|m_caseSensitive
expr_stmt|;
name|parser
operator|.
name|regex
operator|=
name|m_regExp
expr_stmt|;
name|parser
operator|.
name|searchExpression
argument_list|()
expr_stmt|;
name|ast
operator|=
name|parser
operator|.
name|getAST
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|ast
operator|=
literal|null
expr_stmt|;
comment|// nothing to do; set m_ast to null -> regular plaintext search
block|}
name|m_ast
operator|=
name|ast
expr_stmt|;
if|if
condition|(
name|m_ast
operator|!=
literal|null
condition|)
block|{
comment|// do advanced search
name|m_searchRule
operator|=
name|this
expr_stmt|;
block|}
else|else
block|{
comment|// do plaintext search
if|if
condition|(
name|m_regExp
condition|)
name|m_searchRule
operator|=
operator|new
name|RegExpRule
argument_list|(
name|m_caseSensitive
argument_list|,
name|m_searchAllFields
argument_list|,
name|m_searchRequiredFields
argument_list|,
name|m_searchOptionalFields
argument_list|,
name|m_searchGeneralFields
argument_list|)
expr_stmt|;
else|else
name|m_searchRule
operator|=
operator|new
name|SimpleSearchRule
argument_list|(
name|m_caseSensitive
argument_list|,
name|m_searchAllFields
argument_list|,
name|m_searchRequiredFields
argument_list|,
name|m_searchOptionalFields
argument_list|,
name|m_searchGeneralFields
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Parses s and recreates the SearchGroup from it.      *       * @param s      *            The String representation obtained from      *            SearchGroup.toString(), or null if incompatible      */
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
literal|"Internal error: SearchGroup cannot be created from \""
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
name|expression
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|boolean
name|caseSensitive
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|boolean
name|regExp
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|boolean
name|searchAllFields
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|boolean
name|searchRequiredFields
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|boolean
name|searchOptionalFields
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|boolean
name|searchGeneralFields
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
return|return
operator|new
name|SearchGroup
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
name|expression
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|,
name|searchAllFields
argument_list|,
name|searchRequiredFields
argument_list|,
name|searchOptionalFields
argument_list|,
name|searchGeneralFields
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
name|m_searchRule
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
name|m_searchExpression
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
operator|+
name|SEPARATOR
operator|+
operator|(
name|m_caseSensitive
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|SEPARATOR
operator|+
operator|(
name|m_regExp
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|SEPARATOR
operator|+
operator|(
name|m_searchAllFields
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|SEPARATOR
operator|+
operator|(
name|m_searchRequiredFields
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|SEPARATOR
operator|+
operator|(
name|m_searchOptionalFields
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|SEPARATOR
operator|+
operator|(
name|m_searchGeneralFields
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|SEPARATOR
return|;
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
DECL|method|supportsAdd ()
specifier|public
name|boolean
name|supportsAdd
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
DECL|method|supportsRemove ()
specifier|public
name|boolean
name|supportsRemove
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
DECL|method|addSelection (BasePanel basePanel)
specifier|public
name|AbstractUndoableEdit
name|addSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
comment|// nothing to do, add is not supported
return|return
literal|null
return|;
block|}
DECL|method|removeSelection (BasePanel basePanel)
specifier|public
name|AbstractUndoableEdit
name|removeSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
comment|// nothing to do, remove is not supported
return|return
literal|null
return|;
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
name|SearchGroup
operator|)
condition|)
return|return
literal|false
return|;
name|SearchGroup
name|other
init|=
operator|(
name|SearchGroup
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
name|m_searchExpression
operator|.
name|equals
argument_list|(
name|other
operator|.
name|m_searchExpression
argument_list|)
operator|&&
name|m_caseSensitive
operator|==
name|other
operator|.
name|m_caseSensitive
operator|&&
name|m_regExp
operator|==
name|other
operator|.
name|m_regExp
operator|&&
name|m_searchAllFields
operator|==
name|other
operator|.
name|m_searchAllFields
operator|&&
name|m_searchRequiredFields
operator|==
name|other
operator|.
name|m_searchRequiredFields
operator|&&
name|m_searchOptionalFields
operator|==
name|other
operator|.
name|m_searchOptionalFields
operator|&&
name|m_searchGeneralFields
operator|==
name|other
operator|.
name|m_searchGeneralFields
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @see net.sf.jabref.groups.AbstractGroup#contains(java.util.Map,      *      net.sf.jabref.BibtexEntry)      */
DECL|method|contains (Map searchOptions, BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|Map
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|m_searchRule
operator|.
name|applyRule
argument_list|(
name|searchOptions
argument_list|,
name|entry
argument_list|)
operator|==
literal|0
condition|?
literal|false
else|:
literal|true
return|;
block|}
DECL|method|contains (BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
comment|// use dummy map
return|return
name|contains
argument_list|(
operator|new
name|HashMap
argument_list|()
argument_list|,
name|entry
argument_list|)
return|;
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
if|if
condition|(
name|m_ast
operator|==
literal|null
condition|)
return|return
literal|0
return|;
comment|// this instance cannot be used as a SearchRule; should never happen
try|try
block|{
return|return
name|m_treeParser
operator|.
name|apply
argument_list|(
name|m_ast
argument_list|,
name|entry
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|RecognitionException
name|e
parameter_list|)
block|{
return|return
literal|0
return|;
comment|// this should never occur
block|}
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
name|SearchGroup
argument_list|(
name|m_name
argument_list|,
name|m_searchExpression
argument_list|,
name|m_caseSensitive
argument_list|,
name|m_regExp
argument_list|,
name|m_searchAllFields
argument_list|,
name|m_searchRequiredFields
argument_list|,
name|m_searchOptionalFields
argument_list|,
name|m_searchGeneralFields
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
literal|" in SearchGroup.deepCopy()"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
DECL|method|isCaseSensitive ()
specifier|public
name|boolean
name|isCaseSensitive
parameter_list|()
block|{
return|return
name|m_caseSensitive
return|;
block|}
DECL|method|isRegExp ()
specifier|public
name|boolean
name|isRegExp
parameter_list|()
block|{
return|return
name|m_regExp
return|;
block|}
DECL|method|searchAllFields ()
specifier|public
name|boolean
name|searchAllFields
parameter_list|()
block|{
return|return
name|m_searchAllFields
return|;
block|}
DECL|method|searchGeneralFields ()
specifier|public
name|boolean
name|searchGeneralFields
parameter_list|()
block|{
return|return
name|m_searchGeneralFields
return|;
block|}
DECL|method|searchOptionalFields ()
specifier|public
name|boolean
name|searchOptionalFields
parameter_list|()
block|{
return|return
name|m_searchOptionalFields
return|;
block|}
DECL|method|searchRequiredFields ()
specifier|public
name|boolean
name|searchRequiredFields
parameter_list|()
block|{
return|return
name|m_searchRequiredFields
return|;
block|}
block|}
end_class

end_unit

