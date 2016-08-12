begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003-2016 JabRef contributors.  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *  * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
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
name|URL
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
name|Optional
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
name|importer
operator|.
name|fetcher
operator|.
name|ACS
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
name|importer
operator|.
name|fetcher
operator|.
name|ArXiv
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
name|importer
operator|.
name|fetcher
operator|.
name|CrossRef
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
name|importer
operator|.
name|fetcher
operator|.
name|DoiResolution
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
name|importer
operator|.
name|fetcher
operator|.
name|GoogleScholar
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
name|importer
operator|.
name|fetcher
operator|.
name|IEEE
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
name|importer
operator|.
name|fetcher
operator|.
name|ScienceDirect
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
name|importer
operator|.
name|fetcher
operator|.
name|SpringerLink
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
name|util
operator|.
name|DOI
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|model
operator|.
name|entry
operator|.
name|FieldName
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
comment|/**  * Utility class for trying to resolve URLs to full-text PDF for articles.  */
end_comment

begin_class
DECL|class|FulltextFetchers
specifier|public
class|class
name|FulltextFetchers
block|{
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
name|FulltextFetchers
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|finders
specifier|private
specifier|final
name|List
argument_list|<
name|FulltextFetcher
argument_list|>
name|finders
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|FulltextFetchers ()
specifier|public
name|FulltextFetchers
parameter_list|()
block|{
comment|// Ordering is important, authorities first!
comment|// Publisher
name|finders
operator|.
name|add
argument_list|(
operator|new
name|DoiResolution
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|ScienceDirect
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|SpringerLink
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|ACS
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|ArXiv
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|IEEE
argument_list|()
argument_list|)
expr_stmt|;
comment|// Meta search
name|finders
operator|.
name|add
argument_list|(
operator|new
name|GoogleScholar
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|FulltextFetchers (List<FulltextFetcher> fetcher)
specifier|public
name|FulltextFetchers
parameter_list|(
name|List
argument_list|<
name|FulltextFetcher
argument_list|>
name|fetcher
parameter_list|)
block|{
name|finders
operator|.
name|addAll
argument_list|(
name|fetcher
argument_list|)
expr_stmt|;
block|}
DECL|method|findFullTextPDF (BibEntry entry)
specifier|public
name|Optional
argument_list|<
name|URL
argument_list|>
name|findFullTextPDF
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
comment|// for accuracy, fetch DOI first but do not modify entry
name|BibEntry
name|clonedEntry
init|=
operator|(
name|BibEntry
operator|)
name|entry
operator|.
name|clone
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|doi
init|=
name|clonedEntry
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|doi
operator|.
name|isPresent
argument_list|()
operator|||
operator|!
name|DOI
operator|.
name|build
argument_list|(
name|doi
operator|.
name|get
argument_list|()
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|CrossRef
operator|.
name|findDOI
argument_list|(
name|clonedEntry
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|e
lambda|->
name|clonedEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
name|e
operator|.
name|getDOI
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|FulltextFetcher
name|finder
range|:
name|finders
control|)
block|{
try|try
block|{
name|Optional
argument_list|<
name|URL
argument_list|>
name|result
init|=
name|finder
operator|.
name|findFullText
argument_list|(
name|clonedEntry
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|isPresent
argument_list|()
operator|&&
name|MimeTypeDetector
operator|.
name|isPdfContentType
argument_list|(
name|result
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|result
return|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Failed to find fulltext PDF at given URL"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
end_class

end_unit

