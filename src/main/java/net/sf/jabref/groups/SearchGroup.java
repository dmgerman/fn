begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|search
operator|.
name|rules
operator|.
name|RegExpSearchRule
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
name|SearchRule
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
name|rules
operator|.
name|SimpleSearchRule
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
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * @author jzieren  *<p>  *         TODO To change the template for this generated type comment go to Window -  *         Preferences - Java - Code Style - Code Templates  */
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
DECL|field|searchExpression
specifier|private
specifier|final
name|String
name|searchExpression
decl_stmt|;
DECL|field|caseSensitive
specifier|private
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
DECL|field|regExp
specifier|private
specifier|final
name|boolean
name|regExp
decl_stmt|;
DECL|field|ast
specifier|private
specifier|final
name|AST
name|ast
decl_stmt|;
DECL|field|treeParser
specifier|private
specifier|static
specifier|final
name|SearchExpressionTreeParser
name|treeParser
init|=
operator|new
name|SearchExpressionTreeParser
argument_list|()
decl_stmt|;
comment|/**      * If searchExpression is in valid syntax for advanced search,<b>this      *</b> will do the search; otherwise, either<b>RegExpSearchRule</b> or      *<b>SimpleSearchRule</b> will be used.      */
DECL|field|searchRule
specifier|private
specifier|final
name|SearchRule
name|searchRule
decl_stmt|;
comment|/**      * Creates a SearchGroup with the specified properties.      */
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
name|this
operator|.
name|searchExpression
operator|=
name|searchExpression
expr_stmt|;
name|this
operator|.
name|caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|this
operator|.
name|regExp
operator|=
name|regExp
expr_stmt|;
comment|// create AST
name|AST
name|ast
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
name|this
operator|.
name|searchExpression
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|parser
operator|.
name|caseSensitive
operator|=
name|this
operator|.
name|caseSensitive
expr_stmt|;
name|parser
operator|.
name|regex
operator|=
name|this
operator|.
name|regExp
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
comment|// nothing to do; set ast to null -> regular plaintext search
block|}
name|this
operator|.
name|ast
operator|=
name|ast
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|ast
operator|!=
literal|null
condition|)
block|{
comment|// do advanced search
name|searchRule
operator|=
name|this
expr_stmt|;
block|}
else|else
block|{
comment|// do plaintext search
if|if
condition|(
name|this
operator|.
name|regExp
condition|)
block|{
name|searchRule
operator|=
operator|new
name|RegExpSearchRule
argument_list|(
name|this
operator|.
name|caseSensitive
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|searchRule
operator|=
operator|new
name|SimpleSearchRule
argument_list|(
name|this
operator|.
name|caseSensitive
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Parses s and recreates the SearchGroup from it.      *      * @param s The String representation obtained from      *          SearchGroup.toString(), or null if incompatible      */
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
name|SearchGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
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
block|}
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
name|SearchGroup
operator|.
name|ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
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
name|StringUtil
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|AbstractGroup
operator|.
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
name|StringUtil
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|AbstractGroup
operator|.
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
annotation|@
name|Override
DECL|method|getTypeId ()
specifier|public
name|String
name|getTypeId
parameter_list|()
block|{
return|return
name|SearchGroup
operator|.
name|ID
return|;
block|}
comment|/**      * @see net.sf.jabref.groups.AbstractGroup#getSearchRule()      */
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|SearchGroup
operator|.
name|ID
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|m_name
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|m_context
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|searchExpression
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
operator|(
name|caseSensitive
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
operator|(
name|regExp
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|AbstractGroup
operator|.
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
name|searchExpression
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
block|{
return|return
literal|false
return|;
block|}
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
name|searchExpression
operator|.
name|equals
argument_list|(
name|other
operator|.
name|searchExpression
argument_list|)
operator|&&
operator|(
name|caseSensitive
operator|==
name|other
operator|.
name|caseSensitive
operator|)
operator|&&
operator|(
name|regExp
operator|==
name|other
operator|.
name|regExp
operator|)
operator|&&
operator|(
name|getHierarchicalContext
argument_list|()
operator|==
name|other
operator|.
name|getHierarchicalContext
argument_list|()
operator|)
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @see net.sf.jabref.groups.AbstractGroup#contains(java.util.Map,      *      net.sf.jabref.BibtexEntry)      */
annotation|@
name|Override
DECL|method|contains (String searchOptions, BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|String
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
operator|!=
literal|0
return|;
block|}
annotation|@
name|Override
DECL|method|contains (BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|contains
argument_list|(
name|SearchRule
operator|.
name|DUMMY_QUERY
argument_list|,
name|entry
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|applyRule (String searchOptions, BibtexEntry entry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|String
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|ast
operator|==
literal|null
condition|)
block|{
comment|// the searchOptions object is a dummy; we need to insert
comment|// the actual search expression.
return|return
name|searchRule
operator|.
name|applyRule
argument_list|(
name|searchExpression
argument_list|,
name|entry
argument_list|)
return|;
block|}
try|try
block|{
return|return
name|SearchGroup
operator|.
name|treeParser
operator|.
name|apply
argument_list|(
name|ast
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
annotation|@
name|Override
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
name|searchExpression
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
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
name|caseSensitive
return|;
block|}
DECL|method|isRegExp ()
specifier|public
name|boolean
name|isRegExp
parameter_list|()
block|{
return|return
name|regExp
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
operator|new
name|SearchExpressionDescriber
argument_list|(
name|caseSensitive
argument_list|,
name|regExp
argument_list|,
name|searchExpression
argument_list|,
name|ast
argument_list|)
operator|.
name|getDescriptionForPreview
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getShortDescription ()
specifier|public
name|String
name|getShortDescription
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
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
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<i>"
argument_list|)
operator|.
name|append
argument_list|(
name|StringUtil
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
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</b> - "
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
literal|"dynamic group"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" ("
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
literal|"search expression"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|searchExpression
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
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"includes subgroups"
argument_list|)
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
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"refines supergroup"
argument_list|)
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
annotation|@
name|Override
DECL|method|validateSearchStrings (String query)
specifier|public
name|boolean
name|validateSearchStrings
parameter_list|(
name|String
name|query
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

