begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Util
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
name|net
operator|.
name|URLDownload
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

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
name|net
operator|.
name|URL
import|;
end_import

begin_comment
comment|/**  * Convenience class for getting BibTeX entries from the BibSonomy scraper,  * from an URL pointing to an entry.  */
end_comment

begin_class
DECL|class|BibsonomyScraper
specifier|public
class|class
name|BibsonomyScraper
block|{
DECL|field|BIBSONOMY_SCRAPER
specifier|protected
specifier|static
specifier|final
name|String
name|BIBSONOMY_SCRAPER
init|=
literal|"http://scraper.bibsonomy.org/service?url="
decl_stmt|;
DECL|field|BIBSONOMY_SCRAPER_POST
specifier|protected
specifier|static
specifier|final
name|String
name|BIBSONOMY_SCRAPER_POST
init|=
literal|"&format=bibtex"
decl_stmt|;
comment|/**      * Return a BibtexEntry by looking up the given url from the BibSonomy scraper.      * @param entryUrl      * @return      */
DECL|method|getEntry (String entryUrl)
specifier|public
specifier|static
name|BibtexEntry
name|getEntry
parameter_list|(
name|String
name|entryUrl
parameter_list|)
block|{
try|try
block|{
comment|// Replace special characters by corresponding sequences:
name|entryUrl
operator|=
name|entryUrl
operator|.
name|replaceAll
argument_list|(
literal|"%"
argument_list|,
literal|"%25"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|":"
argument_list|,
literal|"%3A"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"/"
argument_list|,
literal|"%2F"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\?"
argument_list|,
literal|"%3F"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&"
argument_list|,
literal|"%26"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"="
argument_list|,
literal|"%3D"
argument_list|)
expr_stmt|;
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|BIBSONOMY_SCRAPER
operator|+
name|entryUrl
operator|+
name|BIBSONOMY_SCRAPER_POST
argument_list|)
decl_stmt|;
name|URLDownload
name|ud
init|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|ud
operator|.
name|setEncoding
argument_list|(
literal|"UTF8"
argument_list|)
expr_stmt|;
name|ud
operator|.
name|download
argument_list|()
expr_stmt|;
name|String
name|bibtex
init|=
name|ud
operator|.
name|getStringContent
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|bibtex
argument_list|)
expr_stmt|;
name|BibtexParser
name|bp
init|=
operator|new
name|BibtexParser
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtex
argument_list|)
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|bp
operator|.
name|parse
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|pr
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
return|return
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
return|;
block|}
else|else
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

