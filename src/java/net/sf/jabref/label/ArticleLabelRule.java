begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   Copyright (C) 2003 Morten O. Alver, Nizar N. Batada    All programs in this directory and   subdirectories are published under the GNU General Public License as   described below.    This program is free software; you can redistribute it and/or modify   it under the terms of the GNU General Public License as published by   the Free Software Foundation; either version 2 of the License, or (at   your option) any later version.    This program is distributed in the hope that it will be useful, but   WITHOUT ANY WARRANTY; without even the implied warranty of   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU   General Public License for more details.    You should have received a copy of the GNU General Public License   along with this program; if not, write to the Free Software   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307   USA    Further information about the GNU GPL is available at:   http://www.gnu.org/copyleft/gpl.ja.html  */
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|ImportFormatReader
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
name|String
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
name|String
name|author
init|=
literal|""
decl_stmt|;
comment|//## to be done: i need to check if the key is unique else need to make another one with suffix
try|try
block|{
name|author
operator|=
operator|(
name|String
operator|)
name|oldEntry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|String
index|[]
name|tokens
init|=
name|author
operator|.
name|split
argument_list|(
literal|"\\band\\b"
argument_list|)
decl_stmt|;
if|if
condition|(
name|tokens
operator|.
name|length
operator|>
literal|0
condition|)
block|{
comment|// if author is empty
if|if
condition|(
name|tokens
index|[
literal|0
index|]
operator|.
name|indexOf
argument_list|(
literal|","
argument_list|)
operator|>
literal|0
condition|)
name|tokens
index|[
literal|0
index|]
operator|=
name|ImportFormatReader
operator|.
name|fixAuthor
argument_list|(
name|tokens
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
comment|// convert lastname, firstname to firstname lastname
name|String
index|[]
name|firstAuthor
init|=
name|tokens
index|[
literal|0
index|]
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|" "
argument_list|)
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
comment|// lastname, firstname
name|newLabel
operator|+=
name|firstAuthor
index|[
name|firstAuthor
operator|.
name|length
operator|-
literal|1
index|]
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
comment|// use the year token
try|try
block|{
if|if
condition|(
operator|!
name|newLabel
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
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
else|else
name|newLabel
operator|=
name|oldLabel
expr_stmt|;
comment|// don't make a key since there is no author
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
comment|// now check for uniqueness
comment|// i need access to basepanes: checkForDuplicateKey
comment|//oldEntry.setField(Globals.KEY_FIELD,newLabel) ;
return|return
name|newLabel
return|;
comment|/* // use the journal name // return the first token 4 wrds or longer, that's not journal // , society, or the like (using the Keyword class) try{  if(oldEntry.getField("journal") != null) { authorTokens = new StringTokenizer( ((String) oldEntry.getField("journal")).replaceAll(","," ").replaceAll("/"," ")) ; String tempString = authorTokens.nextToken() ; tempString = tempString.replaceAll(",","") ; boolean done = false ; while(tempString!=null&& !done ){ tempString = tempString.replaceAll(",","").trim() ; if(tempString.trim().length()> 3&& !KeyWord.isKeyWord(tempString))  { done = true ; } else{  if(authorTokens.hasMoreTokens()){ tempString = authorTokens.nextToken() ; }else{ done = true ; } } }  if(tempString!=null&& (tempString.indexOf("null")<0) ){ newLabel += String.valueOf( tempString.toLowerCase()) ; } } } catch(Throwable t){  System.err.println(t) ; } */
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

