begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  All programs in this directory and subdirectories are published under the   GNU General Public License as described below.   This program is free software; you can redistribute it and/or modify it   under the terms of the GNU General Public License as published by the Free   Software Foundation; either version 2 of the License, or (at your option)   any later version.   This program is distributed in the hope that it will be useful, but WITHOUT   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for   more details.   You should have received a copy of the GNU General Public License along   with this program; if not, write to the Free Software Foundation, Inc., 59   Temple Place, Suite 330, Boston, MA 02111-1307 USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|SearchExpressionLexer
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
name|SearchExpressionParser
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
name|SearchExpressionTreeParser
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
name|SearchExpressionTreeParserTokenTypes
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
comment|/** 	 * If m_searchExpression is in valid syntax for advanced search,<b>this 	 *</b> will do the search; otherwise, either<b>RegExpRule</b> or 	 *<b>SimpleSearchRule</b> will be used. 	 */
DECL|field|m_searchRule
specifier|private
specifier|final
name|SearchRule
name|m_searchRule
decl_stmt|;
comment|/** 	 * Creates a SearchGroup with the specified properties. 	 */
DECL|method|SearchGroup (String name, String searchExpression, boolean caseSensitive, boolean regExp, int context)
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
name|int
name|context
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|context
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
argument_list|)
expr_stmt|;
else|else
name|m_searchRule
operator|=
operator|new
name|SimpleSearchRule
argument_list|(
name|m_caseSensitive
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Parses s and recreates the SearchGroup from it. 	 *  	 * @param s 	 *            The String representation obtained from 	 *            SearchGroup.toString(), or null if incompatible 	 */
DECL|method|fromString (String s, BibtexDatabase db, int version)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|,
name|int
name|version
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
literal|"\". "
operator|+
literal|"Please report this on www.sf.net/projects/jabref"
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
switch|switch
condition|(
name|version
condition|)
block|{
case|case
literal|0
case|:
case|case
literal|1
case|:
case|case
literal|2
case|:
block|{
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
comment|// version 0 contained 4 additional booleans to specify search
comment|// fields; these are ignored now, all fields are always searched
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
name|AbstractGroup
operator|.
name|INDEPENDENT
argument_list|)
return|;
block|}
case|case
literal|3
case|:
block|{
name|String
name|name
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|int
name|context
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
comment|// version 0 contained 4 additional booleans to specify search
comment|// fields; these are ignored now, all fields are always searched
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
name|context
argument_list|)
return|;
block|}
default|default:
throw|throw
operator|new
name|UnsupportedVersionException
argument_list|(
literal|"SearchGroup"
argument_list|,
name|version
argument_list|)
throw|;
block|}
block|}
DECL|method|getTypeId ()
specifier|public
name|String
name|getTypeId
parameter_list|()
block|{
return|return
name|ID
return|;
block|}
comment|/** 	 * @see net.sf.jabref.groups.AbstractGroup#getSearchRule() 	 */
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
comment|/** 	 * Returns a String representation of this object that can be used to 	 * reconstruct it. 	 */
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
name|m_context
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
DECL|method|add (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|add
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
comment|// nothing to do, add is not supported
return|return
literal|null
return|;
block|}
DECL|method|remove (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|remove
parameter_list|(
name|BibtexEntry
index|[]
name|entries
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
name|getHierarchicalContext
argument_list|()
operator|==
name|other
operator|.
name|getHierarchicalContext
argument_list|()
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 *  	 * @see net.sf.jabref.groups.AbstractGroup#contains(java.util.Map, 	 *      net.sf.jabref.BibtexEntry) 	 */
DECL|method|contains (Map<String, String> searchOptions, BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
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
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
argument_list|,
name|entry
argument_list|)
return|;
block|}
DECL|method|applyRule (Map<String, String> searchOptions, BibtexEntry entry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
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
block|{
comment|// the searchOptions object is a dummy; we need to insert
comment|// the actual search expression.
name|searchOptions
operator|.
name|put
argument_list|(
literal|"option"
argument_list|,
name|m_searchExpression
argument_list|)
expr_stmt|;
return|return
name|m_searchRule
operator|.
name|applyRule
argument_list|(
name|searchOptions
argument_list|,
name|entry
argument_list|)
return|;
block|}
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
name|m_context
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
literal|" in SearchGroup.deepCopy(). "
operator|+
literal|"Please report this on www.sf.net/projects/jabref"
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
DECL|method|isDynamic ()
specifier|public
name|boolean
name|isDynamic
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|getDescriptionForPreview
argument_list|(
name|m_searchExpression
argument_list|,
name|m_ast
argument_list|,
name|m_caseSensitive
argument_list|,
name|m_regExp
argument_list|)
return|;
block|}
DECL|method|getDescriptionForPreview (String expr, AST ast, boolean caseSensitive, boolean regExp)
specifier|public
specifier|static
name|String
name|getDescriptionForPreview
parameter_list|(
name|String
name|expr
parameter_list|,
name|AST
name|ast
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|ast
operator|==
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|regExp
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"This group contains entries in which any field contains the regular expression<b>%0</b>"
argument_list|,
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"This group contains entries in which any field contains the term<b>%0</b>"
argument_list|,
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" ("
argument_list|)
operator|.
name|append
argument_list|(
name|caseSensitive
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"case sensitive"
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"case insensitive"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"). "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entries cannot be manually assigned to or removed from this group."
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<p><br>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Hint%c To search specific fields only, enter for example%c<p><tt>author%esmith and title%eelectrical</tt>"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|// describe advanced search expression
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"This group contains entries in which"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|describeNode
argument_list|(
name|ast
argument_list|,
name|regExp
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|caseSensitive
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"The search is case sensitive."
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"The search is case insensitive."
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|describeNode (AST node, boolean regExp, boolean not, boolean and, boolean or)
specifier|protected
specifier|static
name|String
name|describeNode
parameter_list|(
name|AST
name|node
parameter_list|,
name|boolean
name|regExp
parameter_list|,
name|boolean
name|not
parameter_list|,
name|boolean
name|and
parameter_list|,
name|boolean
name|or
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|node
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|SearchExpressionTreeParserTokenTypes
operator|.
name|And
case|:
if|if
condition|(
name|not
condition|)
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"not"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
comment|// if there was an "or" in this subtree so far, braces may be needed
if|if
condition|(
name|or
operator|||
name|not
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"("
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|describeNode
argument_list|(
name|node
operator|.
name|getFirstChild
argument_list|()
argument_list|,
name|regExp
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"and"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|describeNode
argument_list|(
name|node
operator|.
name|getFirstChild
argument_list|()
operator|.
name|getNextSibling
argument_list|()
argument_list|,
name|regExp
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|or
operator|||
name|not
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
case|case
name|SearchExpressionTreeParserTokenTypes
operator|.
name|Or
case|:
if|if
condition|(
name|not
condition|)
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"not"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
comment|// if there was an "and" in this subtree so far, braces may be
comment|// needed
if|if
condition|(
name|and
operator|||
name|not
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"("
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|describeNode
argument_list|(
name|node
operator|.
name|getFirstChild
argument_list|()
argument_list|,
name|regExp
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"or"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|describeNode
argument_list|(
name|node
operator|.
name|getFirstChild
argument_list|()
operator|.
name|getNextSibling
argument_list|()
argument_list|,
name|regExp
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|and
operator|||
name|not
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
case|case
name|SearchExpressionTreeParserTokenTypes
operator|.
name|Not
case|:
return|return
name|describeNode
argument_list|(
name|node
operator|.
name|getFirstChild
argument_list|()
argument_list|,
name|regExp
argument_list|,
operator|!
name|not
argument_list|,
name|and
argument_list|,
name|or
argument_list|)
return|;
default|default:
name|node
operator|=
name|node
operator|.
name|getFirstChild
argument_list|()
expr_stmt|;
specifier|final
name|String
name|field
init|=
name|node
operator|.
name|getText
argument_list|()
decl_stmt|;
specifier|final
name|boolean
name|regExpFieldSpec
init|=
operator|!
name|Pattern
operator|.
name|matches
argument_list|(
literal|"\\w+"
argument_list|,
name|field
argument_list|)
decl_stmt|;
name|node
operator|=
name|node
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
specifier|final
name|int
name|type
init|=
name|node
operator|.
name|getType
argument_list|()
decl_stmt|;
name|node
operator|=
name|node
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
specifier|final
name|String
name|termQuoted
init|=
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|node
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|fieldSpecQuoted
init|=
name|regExpFieldSpec
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"any field that matches the regular expression<b>%0</b>"
argument_list|,
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|field
argument_list|)
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"the field<b>%0</b>"
argument_list|,
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|field
argument_list|)
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|SearchExpressionTreeParserTokenTypes
operator|.
name|LITERAL_contains
case|:
case|case
name|SearchExpressionTreeParserTokenTypes
operator|.
name|EQUAL
case|:
if|if
condition|(
name|regExp
condition|)
return|return
name|not
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 doesn't contain the Regular Expression<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 contains the Regular Expression<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
return|return
name|not
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 doesn't contain the term<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 contains the term<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
case|case
name|SearchExpressionTreeParserTokenTypes
operator|.
name|LITERAL_matches
case|:
case|case
name|SearchExpressionTreeParserTokenTypes
operator|.
name|EEQUAL
case|:
if|if
condition|(
name|regExp
condition|)
return|return
name|not
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 doesn't match the Regular Expression<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 matches the Regular Expression<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
return|return
name|not
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 doesn't match the term<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 matches the term<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
case|case
name|SearchExpressionTreeParserTokenTypes
operator|.
name|NEQUAL
case|:
if|if
condition|(
name|regExp
condition|)
return|return
name|not
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 contains the Regular Expression<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 doesn't contain the Regular Expression<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
return|return
name|not
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 contains the term<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 doesn't contain the term<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
default|default:
return|return
literal|"Internal error: Unknown AST node type. "
operator|+
literal|"Please report this on www.sf.net/projects/jabref"
return|;
comment|// this should never happen
block|}
block|}
block|}
DECL|method|getShortDescription ()
specifier|public
name|String
name|getShortDescription
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"groupShowDynamic"
argument_list|)
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"<i>"
argument_list|)
operator|.
name|append
argument_list|(
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|getName
argument_list|()
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</i>"
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|append
argument_list|(
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</b> - dynamic group ("
operator|+
literal|"search expression:<b>"
argument_list|)
operator|.
name|append
argument_list|(
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|m_searchExpression
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</b>)"
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|AbstractGroup
operator|.
name|INCLUDING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", includes subgroups"
argument_list|)
expr_stmt|;
break|break;
case|case
name|AbstractGroup
operator|.
name|REFINING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", refines supergroup"
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

