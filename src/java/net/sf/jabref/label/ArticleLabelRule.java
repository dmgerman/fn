begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.label
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|label
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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

begin_class
DECL|class|ArticleLabelRule
specifier|public
class|class
name|ArticleLabelRule
extends|extends
name|DefaultLabelRule
block|{
comment|// this is the rule used handle articles
comment|// we try (first author last name)/(year)/(first unique journal word)
DECL|method|applyRule (BibtexEntry oldEntry)
specifier|public
name|BibtexEntry
name|applyRule
parameter_list|(
name|BibtexEntry
name|oldEntry
parameter_list|)
block|{
name|String
name|oldLabel
init|=
call|(
name|String
call|)
argument_list|(
name|oldEntry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|newLabel
init|=
literal|""
decl_stmt|;
name|StringTokenizer
name|authorTokens
init|=
literal|null
decl_stmt|;
comment|// use the author token
try|try
block|{
name|authorTokens
operator|=
operator|new
name|StringTokenizer
argument_list|(
operator|(
name|String
operator|)
name|oldEntry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|,
literal|","
argument_list|)
expr_stmt|;
name|newLabel
operator|+=
name|authorTokens
operator|.
name|nextToken
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\."
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"error getting author: "
operator|+
name|t
argument_list|)
expr_stmt|;
block|}
comment|// use the year token
try|try
block|{
if|if
condition|(
name|oldEntry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|newLabel
operator|+=
name|String
operator|.
name|valueOf
argument_list|(
name|oldEntry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"error getting year: "
operator|+
name|t
argument_list|)
expr_stmt|;
block|}
comment|// use the journal name
comment|// return the first token 4 wrds or longer, that's not journal
comment|// , society, or the like (using the Keyword class)
try|try
block|{
if|if
condition|(
name|oldEntry
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|authorTokens
operator|=
operator|new
name|StringTokenizer
argument_list|(
operator|(
operator|(
name|String
operator|)
name|oldEntry
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
operator|)
operator|.
name|replaceAll
argument_list|(
literal|","
argument_list|,
literal|" "
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"/"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|tempString
init|=
name|authorTokens
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|tempString
operator|=
name|tempString
operator|.
name|replaceAll
argument_list|(
literal|","
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|boolean
name|done
init|=
literal|false
decl_stmt|;
while|while
condition|(
name|tempString
operator|!=
literal|null
operator|&&
operator|!
name|done
condition|)
block|{
name|tempString
operator|=
name|tempString
operator|.
name|replaceAll
argument_list|(
literal|","
argument_list|,
literal|""
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|tempString
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|3
operator|&&
operator|!
name|KeyWord
operator|.
name|isKeyWord
argument_list|(
name|tempString
argument_list|)
condition|)
block|{
name|done
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|authorTokens
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|tempString
operator|=
name|authorTokens
operator|.
name|nextToken
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|done
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|tempString
operator|!=
literal|null
operator|&&
operator|(
name|tempString
operator|.
name|indexOf
argument_list|(
literal|"null"
argument_list|)
operator|<
literal|0
operator|)
condition|)
block|{
name|newLabel
operator|+=
name|String
operator|.
name|valueOf
argument_list|(
name|tempString
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
name|oldEntry
operator|.
name|setField
argument_list|(
name|Globals
operator|.
name|KEY_FIELD
argument_list|,
name|newLabel
argument_list|)
expr_stmt|;
return|return
name|oldEntry
return|;
block|}
comment|//    public static void main(String args[]){
comment|//
comment|//        System.out.println(args[0]) ;
comment|//        BibtexEntry entry = new BibtexEntry("1",BibtexEntryType.ARTICLE) ;
comment|//        entry.setField("journal",args[0]) ;
comment|//        entry.setField("author","jones, b") ;
comment|//        entry.setField("year","1984") ;
comment|//        ArticleLabelRule rule = new ArticleLabelRule() ;
comment|//        entry = rule.applyRule(entry) ;
comment|//        System.out.println(entry.getField(BibtexBaseFrame.KEY_PROPERTY) );
comment|//
comment|//    }
block|}
end_class

end_unit

