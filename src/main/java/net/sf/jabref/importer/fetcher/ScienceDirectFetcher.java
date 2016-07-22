begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

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
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

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
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|importer
operator|.
name|ImportInspector
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
name|importer
operator|.
name|OutputPrinter
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
name|logic
operator|.
name|help
operator|.
name|HelpFile
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|logic
operator|.
name|net
operator|.
name|URLDownload
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  *  * The current ScienceDirect fetcher implementation does no longer work  *  */
end_comment

begin_class
annotation|@
name|Deprecated
DECL|class|ScienceDirectFetcher
specifier|public
class|class
name|ScienceDirectFetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|SCIENCE_DIRECT
specifier|private
specifier|static
specifier|final
name|String
name|SCIENCE_DIRECT
init|=
literal|"ScienceDirect"
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ScienceDirectFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|MAX_PAGES_TO_LOAD
specifier|private
specifier|static
specifier|final
name|int
name|MAX_PAGES_TO_LOAD
init|=
literal|8
decl_stmt|;
DECL|field|WEBSITE_URL
specifier|private
specifier|static
specifier|final
name|String
name|WEBSITE_URL
init|=
literal|"http://www.sciencedirect.com"
decl_stmt|;
DECL|field|SEARCH_URL
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL
init|=
name|ScienceDirectFetcher
operator|.
name|WEBSITE_URL
operator|+
literal|"/science/quicksearch?query="
decl_stmt|;
DECL|field|LINK_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|LINK_PREFIX
init|=
literal|"http://www.sciencedirect.com/science?_ob=ArticleURL&"
decl_stmt|;
DECL|field|LINK_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|LINK_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a href=\""
operator|+
name|ScienceDirectFetcher
operator|.
name|LINK_PREFIX
operator|.
name|replaceAll
argument_list|(
literal|"\\?"
argument_list|,
literal|"\\\\?"
argument_list|)
operator|+
literal|"([^\"]+)\"\""
argument_list|)
decl_stmt|;
DECL|field|stopFetching
specifier|private
name|boolean
name|stopFetching
decl_stmt|;
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|HelpFile
name|getHelpPage
parameter_list|()
block|{
return|return
name|HelpFile
operator|.
name|FETCHER_SCIENCEDIRECT
return|;
block|}
annotation|@
name|Override
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
comment|// No Options panel
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
literal|"ScienceDirect"
return|;
block|}
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
name|stopFetching
operator|=
literal|true
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector dialog, OutputPrinter status)
specifier|public
name|boolean
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|dialog
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
name|stopFetching
operator|=
literal|false
expr_stmt|;
try|try
block|{
name|List
argument_list|<
name|String
argument_list|>
name|citations
init|=
name|getCitations
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|citations
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|citations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No entries found for the search string '%0'"
argument_list|,
name|query
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search %0"
argument_list|,
name|SCIENCE_DIRECT
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
name|cit
range|:
name|citations
control|)
block|{
if|if
condition|(
name|stopFetching
condition|)
block|{
break|break;
block|}
name|BibsonomyScraper
operator|.
name|getEntry
argument_list|(
name|cit
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|dialog
operator|::
name|addEntry
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|setProgress
argument_list|(
operator|++
name|i
argument_list|,
name|citations
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Communcation problems"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from %0"
argument_list|,
name|SCIENCE_DIRECT
argument_list|)
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      *      * @param query      *            The search term to query JStor for.      * @return a list of IDs      * @throws java.io.IOException      */
DECL|method|getCitations (String query)
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getCitations
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|urlQuery
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|ids
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|urlQuery
operator|=
name|ScienceDirectFetcher
operator|.
name|SEARCH_URL
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|query
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|count
init|=
literal|1
decl_stmt|;
name|String
name|nextPage
decl_stmt|;
while|while
condition|(
operator|(
operator|(
name|nextPage
operator|=
name|getCitationsFromUrl
argument_list|(
name|urlQuery
argument_list|,
name|ids
argument_list|)
operator|)
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|count
operator|<
name|ScienceDirectFetcher
operator|.
name|MAX_PAGES_TO_LOAD
operator|)
condition|)
block|{
name|urlQuery
operator|=
name|nextPage
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
return|return
name|ids
return|;
block|}
DECL|method|getCitationsFromUrl (String urlQuery, List<String> ids)
specifier|private
specifier|static
name|String
name|getCitationsFromUrl
parameter_list|(
name|String
name|urlQuery
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|ids
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|cont
init|=
operator|new
name|URLDownload
argument_list|(
name|urlQuery
argument_list|)
operator|.
name|downloadToString
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
name|Matcher
name|m
init|=
name|ScienceDirectFetcher
operator|.
name|LINK_PATTERN
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|ids
operator|.
name|add
argument_list|(
name|ScienceDirectFetcher
operator|.
name|LINK_PREFIX
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|cont
operator|=
name|cont
operator|.
name|substring
argument_list|(
name|m
operator|.
name|end
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|=
name|ScienceDirectFetcher
operator|.
name|LINK_PATTERN
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

