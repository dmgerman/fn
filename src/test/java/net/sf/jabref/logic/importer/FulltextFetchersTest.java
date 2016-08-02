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
name|net
operator|.
name|MalformedURLException
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
name|Arrays
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Ignore
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|FulltextFetchersTest
specifier|public
class|class
name|FulltextFetchersTest
block|{
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|entry
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Ignore
annotation|@
name|Test
DECL|method|acceptPdfUrls ()
specifier|public
name|void
name|acceptPdfUrls
parameter_list|()
throws|throws
name|MalformedURLException
block|{
name|URL
name|pdfUrl
init|=
operator|new
name|URL
argument_list|(
literal|"http://docs.oasis-open.org/wsbpel/2.0/OS/wsbpel-v2.0-OS.pdf"
argument_list|)
decl_stmt|;
name|FulltextFetcher
name|finder
init|=
parameter_list|(
name|e
parameter_list|)
lambda|->
name|Optional
operator|.
name|of
argument_list|(
name|pdfUrl
argument_list|)
decl_stmt|;
name|FulltextFetchers
name|fetcher
init|=
operator|new
name|FulltextFetchers
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|finder
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|pdfUrl
argument_list|)
argument_list|,
name|fetcher
operator|.
name|findFullTextPDF
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Ignore
comment|// Fails on travis
annotation|@
name|Test
DECL|method|rejectNonPdfUrls ()
specifier|public
name|void
name|rejectNonPdfUrls
parameter_list|()
throws|throws
name|MalformedURLException
block|{
name|URL
name|pdfUrl
init|=
operator|new
name|URL
argument_list|(
literal|"https://github.com/JabRef/jabref/blob/master/README.md"
argument_list|)
decl_stmt|;
name|FulltextFetcher
name|finder
init|=
parameter_list|(
name|e
parameter_list|)
lambda|->
name|Optional
operator|.
name|of
argument_list|(
name|pdfUrl
argument_list|)
decl_stmt|;
name|FulltextFetchers
name|fetcher
init|=
operator|new
name|FulltextFetchers
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|finder
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|fetcher
operator|.
name|findFullTextPDF
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
