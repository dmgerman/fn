begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
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
name|JabRefGUI
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
name|JabRefPreferences
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
name|gui
operator|.
name|JabRefFrame
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
name|Assert
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
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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

begin_class
DECL|class|EntryFromPDFCreatorTest
specifier|public
class|class
name|EntryFromPDFCreatorTest
block|{
DECL|field|entryCreator
specifier|private
name|EntryFromPDFCreator
name|entryCreator
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
comment|// Needed to initialize ExternalFileTypes
name|entryCreator
operator|=
operator|new
name|EntryFromPDFCreator
argument_list|()
expr_stmt|;
comment|// Needed for PdfImporter - still not enough
name|JabRefGUI
operator|.
name|setMainFrame
argument_list|(
name|mock
argument_list|(
name|JabRefFrame
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPDFFileFilter ()
specifier|public
name|void
name|testPDFFileFilter
parameter_list|()
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|entryCreator
operator|.
name|accept
argument_list|(
operator|new
name|File
argument_list|(
literal|"aPDF.pdf"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|entryCreator
operator|.
name|accept
argument_list|(
operator|new
name|File
argument_list|(
literal|"aPDF.PDF"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|entryCreator
operator|.
name|accept
argument_list|(
operator|new
name|File
argument_list|(
literal|"foo.jpg"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCreationOfEntryNoPDF ()
specifier|public
name|void
name|testCreationOfEntryNoPDF
parameter_list|()
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
init|=
name|entryCreator
operator|.
name|createEntry
argument_list|(
name|ImportDataTest
operator|.
name|NOT_EXISTING_PDF
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|entry
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testCreationOfEntryNotInDatabase ()
specifier|public
name|void
name|testCreationOfEntryNotInDatabase
parameter_list|()
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
init|=
name|entryCreator
operator|.
name|createEntry
argument_list|(
name|ImportDataTest
operator|.
name|FILE_NOT_IN_DATABASE
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|entry
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|entry
operator|.
name|get
argument_list|()
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
operator|.
name|endsWith
argument_list|(
literal|":PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|ImportDataTest
operator|.
name|FILE_NOT_IN_DATABASE
operator|.
name|getName
argument_list|()
argument_list|,
name|entry
operator|.
name|get
argument_list|()
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

