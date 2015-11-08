begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015 Oscar Gustafsson.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
package|;
end_package

begin_import
import|import
name|org
operator|.
name|json
operator|.
name|JSONObject
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntryTypes
import|;
end_import

begin_class
DECL|class|BibJSONConverterTest
specifier|public
class|class
name|BibJSONConverterTest
block|{
annotation|@
name|Test
DECL|method|testBibJSONConverter ()
specifier|public
name|void
name|testBibJSONConverter
parameter_list|()
block|{
name|String
name|jsonString
init|=
operator|new
name|String
argument_list|(
literal|"{\n\"title\": \"Design of Finite Word Length Linear-Phase FIR Filters in the Logarithmic Number System Domain\",\n"
operator|+
literal|"\"journal\": {\n\"publisher\": \"Hindawi Publishing Corporation\",\n\"language\": ["
operator|+
literal|"\"English\"],\n\"title\": \"VLSI Design\",\"country\": \"US\",\"volume\": \"2014\""
operator|+
literal|"},\"author\":[{\"name\": \"Syed Asad Alam\"},{\"name\": \"Oscar Gustafsson\""
operator|+
literal|"}\n],\n\"link\":[{\"url\": \"http://dx.doi.org/10.1155/2014/217495\","
operator|+
literal|"\"type\": \"fulltext\"}],\"year\":\"2014\",\"identifier\":[{"
operator|+
literal|"\"type\": \"pissn\",\"id\": \"1065-514X\"},\n{\"type\": \"eissn\","
operator|+
literal|"\"id\": \"1563-5171\"},{\"type\": \"doi\",\"id\": \"10.1155/2014/217495\""
operator|+
literal|"}],\"created_date\":\"2014-05-09T19:38:31Z\"}\""
argument_list|)
decl_stmt|;
name|JSONObject
name|jo
init|=
operator|new
name|JSONObject
argument_list|(
name|jsonString
argument_list|)
decl_stmt|;
name|BibtexEntry
name|be
init|=
name|BibJSONConverter
operator|.
name|BibJSONtoBibtex
argument_list|(
name|jo
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|be
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"VLSI Design"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"10.1155/2014/217495"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Syed Asad Alam and Oscar Gustafsson"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Design of Finite Word Length Linear-Phase FIR Filters in the Logarithmic Number System Domain"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2014"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

