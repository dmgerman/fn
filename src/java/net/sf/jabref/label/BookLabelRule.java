begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nathan Dunn, Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|BibtexEntry
import|;
end_import

begin_class
DECL|class|BookLabelRule
specifier|public
class|class
name|BookLabelRule
extends|extends
name|DefaultLabelRule
block|{
comment|// this is the rule used handle articles
comment|// we try (first author)/(year)
DECL|method|applyRule (BibtexEntry oldEntry)
specifier|public
name|String
name|applyRule
parameter_list|(
name|BibtexEntry
name|oldEntry
parameter_list|)
block|{
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
if|if
condition|(
operator|(
name|String
operator|)
name|oldEntry
operator|.
name|getField
argument_list|(
literal|"author"
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
block|}
elseif|else
if|if
condition|(
operator|(
name|String
operator|)
name|oldEntry
operator|.
name|getField
argument_list|(
literal|"editor"
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
name|String
operator|)
name|oldEntry
operator|.
name|getField
argument_list|(
literal|"editor"
argument_list|)
argument_list|,
literal|","
argument_list|)
expr_stmt|;
block|}
name|newLabel
operator|+=
name|authorTokens
operator|.
name|nextToken
argument_list|()
operator|.
name|toLowerCase
argument_list|()
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
literal|"error getting author/editor: "
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
literal|"error getting author: "
operator|+
name|t
argument_list|)
expr_stmt|;
block|}
name|newLabel
operator|+=
literal|"book"
expr_stmt|;
comment|//	oldEntry.setField(Globals.KEY_FIELD,newLabel) ;
return|return
name|newLabel
return|;
block|}
comment|//    public static void main(String args[]){
comment|//
comment|//        System.out.println(args[0]) ;
comment|//        BibtexEntry entry = new BibtexEntry("1",BibtexEntryType.ARTICLE) ;
comment|//        entry.setField("journal",args[0]) ;
comment|//        entry.setField("author","jones, b") ;
comment|//        entry.setField("year","1984") ;
comment|//        BookLabelRule rule = new BookLabelRule() ;
comment|//        entry = rule.applyRule(entry) ;
comment|////        System.out.println(entry.getField("journal") );
comment|//        System.out.println(entry.getField(BibtexBaseFrame.KEY_PROPERTY) );
comment|//
comment|//    }
block|}
end_class

end_unit

