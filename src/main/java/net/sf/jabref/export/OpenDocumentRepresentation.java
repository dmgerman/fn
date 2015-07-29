begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2014 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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
name|Collection
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
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|GetOpenOfficeType
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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|RemoveBrackets
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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|RemoveWhitespace
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|BasicEventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|SortedList
import|;
end_import

begin_comment
comment|/**  * @author Morten O. Alver.  * Based on net.sf.jabref.MODSDatabase by Michael Wrighton  *  */
end_comment

begin_class
DECL|class|OpenDocumentRepresentation
class|class
name|OpenDocumentRepresentation
block|{
DECL|field|entries
specifier|private
specifier|final
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibtexDatabase
name|database
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|OpenDocumentRepresentation (BibtexDatabase database, Set<String> keySet)
specifier|public
name|OpenDocumentRepresentation
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
comment|// Make a list of comparators for sorting the entries:
name|List
argument_list|<
name|FieldComparator
argument_list|>
name|comparators
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FieldComparator
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FieldComparator
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FieldComparator
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
expr_stmt|;
comment|// Use glazed lists to get a sorted view of the entries:
name|BasicEventList
argument_list|<
name|BibtexEntry
argument_list|>
name|entryList
init|=
operator|new
name|BasicEventList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Set up a list of all entries, if keySet==null, or the entries whose
comment|// ids are in keySet, otherwise:
if|if
condition|(
name|keySet
operator|==
literal|null
condition|)
block|{
name|entryList
operator|.
name|addAll
argument_list|(
name|database
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|String
name|key
range|:
name|keySet
control|)
block|{
name|entryList
operator|.
name|add
argument_list|(
name|database
operator|.
name|getEntryById
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|entries
operator|=
operator|new
name|SortedList
argument_list|(
name|entryList
argument_list|,
operator|new
name|FieldComparatorStack
argument_list|(
name|comparators
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getDOMrepresentation ()
specifier|public
name|Document
name|getDOMrepresentation
parameter_list|()
block|{
name|Document
name|result
init|=
literal|null
decl_stmt|;
try|try
block|{
name|DocumentBuilder
name|dbuild
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|result
operator|=
name|dbuild
operator|.
name|newDocument
argument_list|()
expr_stmt|;
name|Element
name|collection
init|=
name|result
operator|.
name|createElement
argument_list|(
literal|"office:document-content"
argument_list|)
decl_stmt|;
comment|//collection.setAttribute("xmlns", "http://openoffice.org/2000/office");
name|collection
operator|.
name|setAttribute
argument_list|(
literal|"xmlns:office"
argument_list|,
literal|"urn:oasis:names:tc:opendocument:xmlns:office:1.0"
argument_list|)
expr_stmt|;
name|collection
operator|.
name|setAttribute
argument_list|(
literal|"xmlns:style"
argument_list|,
literal|"urn:oasis:names:tc:opendocument:xmlns:style:1.0"
argument_list|)
expr_stmt|;
name|collection
operator|.
name|setAttribute
argument_list|(
literal|"xmlns:text"
argument_list|,
literal|"urn:oasis:names:tc:opendocument:xmlns:text:1.0"
argument_list|)
expr_stmt|;
name|collection
operator|.
name|setAttribute
argument_list|(
literal|"xmlns:table"
argument_list|,
literal|"urn:oasis:names:tc:opendocument:xmlns:table:1.0"
argument_list|)
expr_stmt|;
name|collection
operator|.
name|setAttribute
argument_list|(
literal|"xmlns:meta"
argument_list|,
literal|"urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
argument_list|)
expr_stmt|;
name|collection
operator|.
name|setAttribute
argument_list|(
literal|"office:version"
argument_list|,
literal|"1.0"
argument_list|)
expr_stmt|;
name|collection
operator|.
name|setAttribute
argument_list|(
literal|"xmlns:fo"
argument_list|,
literal|"urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
argument_list|)
expr_stmt|;
name|collection
operator|.
name|setAttribute
argument_list|(
literal|"xmlns:xlink"
argument_list|,
literal|"http://www.w3.org/1999/xlink"
argument_list|)
expr_stmt|;
name|Element
name|el
init|=
name|result
operator|.
name|createElement
argument_list|(
literal|"office:scripts"
argument_list|)
decl_stmt|;
name|collection
operator|.
name|appendChild
argument_list|(
name|el
argument_list|)
expr_stmt|;
name|el
operator|=
name|result
operator|.
name|createElement
argument_list|(
literal|"office:automatic-styles"
argument_list|)
expr_stmt|;
name|Element
name|el2
init|=
name|result
operator|.
name|createElement
argument_list|(
literal|"style:style"
argument_list|)
decl_stmt|;
name|el2
operator|.
name|setAttribute
argument_list|(
literal|"style:name"
argument_list|,
literal|"ro1"
argument_list|)
expr_stmt|;
name|el2
operator|.
name|setAttribute
argument_list|(
literal|"style:family"
argument_list|,
literal|"table-row"
argument_list|)
expr_stmt|;
name|Element
name|el3
init|=
name|result
operator|.
name|createElement
argument_list|(
literal|"style.table-row-properties"
argument_list|)
decl_stmt|;
name|el3
operator|.
name|setAttribute
argument_list|(
literal|"style:row-height"
argument_list|,
literal|"0.1681inch"
argument_list|)
expr_stmt|;
name|el3
operator|.
name|setAttribute
argument_list|(
literal|"fo:break-before"
argument_list|,
literal|"auto"
argument_list|)
expr_stmt|;
name|el3
operator|.
name|setAttribute
argument_list|(
literal|"style:use-optimal-row-height"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|el2
operator|.
name|appendChild
argument_list|(
name|el3
argument_list|)
expr_stmt|;
name|el
operator|.
name|appendChild
argument_list|(
name|el2
argument_list|)
expr_stmt|;
name|el2
operator|=
name|result
operator|.
name|createElement
argument_list|(
literal|"style:style"
argument_list|)
expr_stmt|;
name|el2
operator|.
name|setAttribute
argument_list|(
literal|"style:name"
argument_list|,
literal|"ta1"
argument_list|)
expr_stmt|;
name|el2
operator|.
name|setAttribute
argument_list|(
literal|"style:family"
argument_list|,
literal|"table"
argument_list|)
expr_stmt|;
name|el2
operator|.
name|setAttribute
argument_list|(
literal|"style:master-page-name"
argument_list|,
literal|"Default"
argument_list|)
expr_stmt|;
name|el3
operator|=
name|result
operator|.
name|createElement
argument_list|(
literal|"style:properties"
argument_list|)
expr_stmt|;
name|el3
operator|.
name|setAttribute
argument_list|(
literal|"table:display"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|el2
operator|.
name|appendChild
argument_list|(
name|el3
argument_list|)
expr_stmt|;
name|el
operator|.
name|appendChild
argument_list|(
name|el2
argument_list|)
expr_stmt|;
name|collection
operator|.
name|appendChild
argument_list|(
name|el
argument_list|)
expr_stmt|;
name|Element
name|body
init|=
name|result
operator|.
name|createElement
argument_list|(
literal|"office:body"
argument_list|)
decl_stmt|;
name|Element
name|spreadsheet
init|=
name|result
operator|.
name|createElement
argument_list|(
literal|"office:spreadsheet"
argument_list|)
decl_stmt|;
name|Element
name|table
init|=
name|result
operator|.
name|createElement
argument_list|(
literal|"table:table"
argument_list|)
decl_stmt|;
name|table
operator|.
name|setAttribute
argument_list|(
literal|"table:name"
argument_list|,
literal|"biblio"
argument_list|)
expr_stmt|;
name|table
operator|.
name|setAttribute
argument_list|(
literal|"table.style-name"
argument_list|,
literal|"ta1"
argument_list|)
expr_stmt|;
name|Element
name|row
init|=
name|result
operator|.
name|createElement
argument_list|(
literal|"table:table-row"
argument_list|)
decl_stmt|;
name|row
operator|.
name|setAttribute
argument_list|(
literal|"table.style-name"
argument_list|,
literal|"ro1"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Identifier"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Type"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Address"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Assignee"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Annote"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Author"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Booktitle"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Chapter"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Day"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Dayfiled"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Edition"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Editor"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Howpublish"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Institution"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Journal"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Language"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Month"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Monthfiled"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Nationality"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Note"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Number"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Organization"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Pages"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Publisher"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Revision"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"School"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Series"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Title"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"RepType"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Volume"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Year"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Yearfiled"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"URL"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Custom1"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Custom2"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Custom3"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Custom4"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"Custom5"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|"ISBN"
argument_list|)
expr_stmt|;
name|table
operator|.
name|appendChild
argument_list|(
name|row
argument_list|)
expr_stmt|;
for|for
control|(
name|BibtexEntry
name|e
range|:
name|entries
control|)
block|{
name|row
operator|=
name|result
operator|.
name|createElement
argument_list|(
literal|"table:table-row"
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
operator|new
name|GetOpenOfficeType
argument_list|()
operator|.
name|format
argument_list|(
name|e
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"address"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"assignee"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"annote"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
comment|//new AuthorLastFirst().format(getField(e, "author")));
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"booktitle"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"chapter"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"day"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"dayfiled"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"edition"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"editor"
argument_list|)
argument_list|)
expr_stmt|;
comment|//new AuthorLastFirst().format(getField(e, "editor")));
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"howpublished"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"institution"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"language"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"monthfiled"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"nationality"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"note"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"number"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"organization"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"revision"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"school"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"series"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
operator|new
name|RemoveWhitespace
argument_list|()
operator|.
name|format
argument_list|(
operator|new
name|RemoveBrackets
argument_list|()
operator|.
name|format
argument_list|(
name|getField
argument_list|(
name|e
argument_list|,
literal|"title"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"reporttype"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"yearfiled"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"url"
argument_list|)
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|addTableCell
argument_list|(
name|result
argument_list|,
name|row
argument_list|,
name|getField
argument_list|(
name|e
argument_list|,
literal|"isbn"
argument_list|)
argument_list|)
expr_stmt|;
name|table
operator|.
name|appendChild
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
name|spreadsheet
operator|.
name|appendChild
argument_list|(
name|table
argument_list|)
expr_stmt|;
name|body
operator|.
name|appendChild
argument_list|(
name|spreadsheet
argument_list|)
expr_stmt|;
name|collection
operator|.
name|appendChild
argument_list|(
name|body
argument_list|)
expr_stmt|;
name|result
operator|.
name|appendChild
argument_list|(
name|collection
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Exception caught..."
operator|+
name|e
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|getField (BibtexEntry e, String field)
specifier|private
name|String
name|getField
parameter_list|(
name|BibtexEntry
name|e
parameter_list|,
name|String
name|field
parameter_list|)
block|{
name|String
name|s
init|=
name|BibtexDatabase
operator|.
name|getResolvedField
argument_list|(
name|field
argument_list|,
name|e
argument_list|,
name|database
argument_list|)
decl_stmt|;
return|return
name|s
operator|==
literal|null
condition|?
literal|""
else|:
name|s
return|;
block|}
DECL|method|addTableCell (Document doc, Element parent, String content)
specifier|private
name|void
name|addTableCell
parameter_list|(
name|Document
name|doc
parameter_list|,
name|Element
name|parent
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|Element
name|cell
init|=
name|doc
operator|.
name|createElement
argument_list|(
literal|"table:table-cell"
argument_list|)
decl_stmt|;
name|Element
name|text
init|=
name|doc
operator|.
name|createElement
argument_list|(
literal|"text:p"
argument_list|)
decl_stmt|;
name|Text
name|textNode
init|=
name|doc
operator|.
name|createTextNode
argument_list|(
name|content
argument_list|)
decl_stmt|;
name|text
operator|.
name|appendChild
argument_list|(
name|textNode
argument_list|)
expr_stmt|;
comment|//text.setTextContent(content);
name|cell
operator|.
name|appendChild
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|parent
operator|.
name|appendChild
argument_list|(
name|cell
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

