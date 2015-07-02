begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2011 Sascha Hunold.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
DECL|class|DBLPHelper
class|class
name|DBLPHelper
block|{
DECL|field|cleaner
specifier|private
specifier|final
name|DBLPQueryCleaner
name|cleaner
init|=
operator|new
name|DBLPQueryCleaner
argument_list|()
decl_stmt|;
comment|/*      * This is a small helper class that cleans the user submitted query. Right      * now, we cannot search for ":" on dblp.org. So, we remove colons from the      * user submitted search string. Also, the search is case sensitive if we      * use capitals. So, we better change the text to lower case.      */
DECL|class|DBLPQueryCleaner
class|class
name|DBLPQueryCleaner
block|{
DECL|method|cleanQuery (final String query)
specifier|public
name|String
name|cleanQuery
parameter_list|(
specifier|final
name|String
name|query
parameter_list|)
block|{
name|String
name|cleaned
init|=
name|query
decl_stmt|;
name|cleaned
operator|=
name|cleaned
operator|.
name|replaceAll
argument_list|(
literal|"-"
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
name|cleaned
operator|=
name|cleaned
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|"%20"
argument_list|)
expr_stmt|;
name|cleaned
operator|=
name|cleaned
operator|.
name|replaceAll
argument_list|(
literal|":"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|cleaned
operator|=
name|cleaned
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
return|return
name|cleaned
return|;
block|}
block|}
comment|/**      *      * @param query      *            string with the user query      * @return a string with the user query, but compatible with dblp.org      */
DECL|method|cleanDBLPQuery (String query)
specifier|public
name|String
name|cleanDBLPQuery
parameter_list|(
name|String
name|query
parameter_list|)
block|{
return|return
name|cleaner
operator|.
name|cleanQuery
argument_list|(
name|query
argument_list|)
return|;
block|}
comment|/**      * Takes an HTML file (as String) as input and extracts the bibtex      * information. After that, it will convert it into a BibtexEntry and return      * it (them).      *      * @param page      *            page as String      * @return list of BibtexEntry      */
DECL|method|getBibTexFromPage (final String page)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|getBibTexFromPage
parameter_list|(
specifier|final
name|String
name|page
parameter_list|)
block|{
specifier|final
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|bibtexList
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
specifier|final
name|String
name|startPattern
init|=
literal|"<pre class=\"verbatim select-on-click\">"
decl_stmt|;
specifier|final
name|String
name|endPattern
init|=
literal|"</pre>"
decl_stmt|;
name|String
name|tmpStr
init|=
name|page
decl_stmt|;
name|int
name|startIdx
init|=
name|tmpStr
operator|.
name|indexOf
argument_list|(
name|startPattern
argument_list|)
decl_stmt|;
name|int
name|endIdx
init|=
name|tmpStr
operator|.
name|indexOf
argument_list|(
name|endPattern
argument_list|)
decl_stmt|;
comment|// this entry exists for sure
name|String
name|entry1
init|=
name|tmpStr
operator|.
name|substring
argument_list|(
name|startIdx
operator|+
name|startPattern
operator|.
name|length
argument_list|()
argument_list|,
name|endIdx
argument_list|)
decl_stmt|;
name|entry1
operator|=
name|cleanEntry
argument_list|(
name|entry1
argument_list|)
expr_stmt|;
name|bibtexList
operator|.
name|add
argument_list|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
name|entry1
argument_list|)
argument_list|)
expr_stmt|;
comment|// System.out.println("'" + entry1 + "'");
comment|// let's see whether there is another entry (crossref)
name|tmpStr
operator|=
name|tmpStr
operator|.
name|substring
argument_list|(
name|endIdx
operator|+
name|endPattern
operator|.
name|length
argument_list|()
argument_list|,
name|tmpStr
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|startIdx
operator|=
name|tmpStr
operator|.
name|indexOf
argument_list|(
name|startPattern
argument_list|)
expr_stmt|;
if|if
condition|(
name|startIdx
operator|!=
operator|-
literal|1
condition|)
block|{
name|endIdx
operator|=
name|tmpStr
operator|.
name|indexOf
argument_list|(
name|endPattern
argument_list|)
expr_stmt|;
comment|// this entry exists for sure
name|String
name|entry2
init|=
name|tmpStr
operator|.
name|substring
argument_list|(
name|startIdx
operator|+
name|startPattern
operator|.
name|length
argument_list|()
argument_list|,
name|endIdx
argument_list|)
decl_stmt|;
name|entry2
operator|=
name|cleanEntry
argument_list|(
name|entry2
argument_list|)
expr_stmt|;
name|bibtexList
operator|.
name|add
argument_list|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
name|entry2
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|bibtexList
return|;
block|}
DECL|method|cleanEntry (final String bibEntry)
specifier|private
name|String
name|cleanEntry
parameter_list|(
specifier|final
name|String
name|bibEntry
parameter_list|)
block|{
return|return
name|bibEntry
operator|.
name|replaceFirst
argument_list|(
literal|"<a href=\".*\">DBLP</a>"
argument_list|,
literal|"DBLP"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

