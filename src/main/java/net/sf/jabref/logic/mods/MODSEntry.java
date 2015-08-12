begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.mods
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|mods
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|OutputKeys
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|Transformer
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|TransformerFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|dom
operator|.
name|DOMSource
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|stream
operator|.
name|StreamResult
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexEntryTypes
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
name|LayoutFormatter
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
name|XMLChars
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
name|Node
import|;
end_import

begin_comment
comment|/**  * @author Michael Wrighton  *  */
end_comment

begin_class
DECL|class|MODSEntry
class|class
name|MODSEntry
block|{
DECL|field|entryType
specifier|private
name|String
name|entryType
init|=
literal|"mods"
decl_stmt|;
comment|// could also be relatedItem
DECL|field|id
specifier|private
name|String
name|id
decl_stmt|;
DECL|field|authors
specifier|private
name|List
argument_list|<
name|PersonName
argument_list|>
name|authors
decl_stmt|;
comment|// should really be handled with an enum
DECL|field|issuance
specifier|private
name|String
name|issuance
init|=
literal|"monographic"
decl_stmt|;
DECL|field|pages
specifier|private
name|PageNumbers
name|pages
decl_stmt|;
DECL|field|publisher
specifier|private
name|String
name|publisher
decl_stmt|;
DECL|field|date
specifier|private
name|String
name|date
decl_stmt|;
DECL|field|title
specifier|private
name|String
name|title
decl_stmt|;
DECL|field|number
specifier|private
name|String
name|number
decl_stmt|;
DECL|field|volume
specifier|private
name|String
name|volume
decl_stmt|;
DECL|field|genre
specifier|private
name|String
name|genre
decl_stmt|;
DECL|field|handledExtensions
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|handledExtensions
decl_stmt|;
DECL|field|host
specifier|private
name|MODSEntry
name|host
decl_stmt|;
DECL|field|extensionFields
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|extensionFields
decl_stmt|;
DECL|field|BIBTEX
specifier|private
specifier|static
specifier|final
name|String
name|BIBTEX
init|=
literal|"bibtex_"
decl_stmt|;
DECL|field|CHARFORMAT
specifier|private
specifier|final
name|boolean
name|CHARFORMAT
init|=
literal|false
decl_stmt|;
DECL|method|MODSEntry ()
specifier|private
name|MODSEntry
parameter_list|()
block|{
name|extensionFields
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|handledExtensions
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
DECL|method|MODSEntry (BibtexEntry bibtex)
specifier|public
name|MODSEntry
parameter_list|(
name|BibtexEntry
name|bibtex
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|handledExtensions
operator|.
name|add
argument_list|(
name|MODSEntry
operator|.
name|BIBTEX
operator|+
literal|"publisher"
argument_list|)
expr_stmt|;
name|handledExtensions
operator|.
name|add
argument_list|(
name|MODSEntry
operator|.
name|BIBTEX
operator|+
literal|"title"
argument_list|)
expr_stmt|;
name|handledExtensions
operator|.
name|add
argument_list|(
name|MODSEntry
operator|.
name|BIBTEX
operator|+
literal|"bibtexkey"
argument_list|)
expr_stmt|;
name|handledExtensions
operator|.
name|add
argument_list|(
name|MODSEntry
operator|.
name|BIBTEX
operator|+
literal|"author"
argument_list|)
expr_stmt|;
name|populateFromBibtex
argument_list|(
name|bibtex
argument_list|)
expr_stmt|;
block|}
DECL|method|populateFromBibtex (BibtexEntry bibtex)
specifier|private
name|void
name|populateFromBibtex
parameter_list|(
name|BibtexEntry
name|bibtex
parameter_list|)
block|{
name|LayoutFormatter
name|chars
init|=
operator|new
name|XMLChars
argument_list|()
decl_stmt|;
if|if
condition|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|CHARFORMAT
condition|)
block|{
name|title
operator|=
name|chars
operator|.
name|format
argument_list|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|title
operator|=
name|bibtex
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|CHARFORMAT
condition|)
block|{
name|publisher
operator|=
name|chars
operator|.
name|format
argument_list|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|publisher
operator|=
name|bibtex
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"bibtexkey"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|id
operator|=
name|bibtex
operator|.
name|getField
argument_list|(
literal|"bibtexkey"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"place"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|place
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|CHARFORMAT
condition|)
block|{
name|place
operator|=
name|chars
operator|.
name|format
argument_list|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"place"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|place
operator|=
name|bibtex
operator|.
name|getField
argument_list|(
literal|"place"
argument_list|)
expr_stmt|;
block|}
block|}
name|date
operator|=
name|getDate
argument_list|(
name|bibtex
argument_list|)
expr_stmt|;
name|genre
operator|=
name|getMODSgenre
argument_list|(
name|bibtex
argument_list|)
expr_stmt|;
if|if
condition|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|authors
operator|=
name|getAuthors
argument_list|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|bibtex
operator|.
name|getType
argument_list|()
operator|==
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|)
operator|||
operator|(
name|bibtex
operator|.
name|getType
argument_list|()
operator|==
name|BibtexEntryTypes
operator|.
name|INPROCEEDINGS
operator|)
condition|)
block|{
name|host
operator|=
operator|new
name|MODSEntry
argument_list|()
expr_stmt|;
name|host
operator|.
name|entryType
operator|=
literal|"relatedItem"
expr_stmt|;
name|host
operator|.
name|title
operator|=
name|bibtex
operator|.
name|getField
argument_list|(
literal|"booktitle"
argument_list|)
expr_stmt|;
name|host
operator|.
name|publisher
operator|=
name|bibtex
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
expr_stmt|;
name|host
operator|.
name|number
operator|=
name|bibtex
operator|.
name|getField
argument_list|(
literal|"number"
argument_list|)
expr_stmt|;
if|if
condition|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|host
operator|.
name|volume
operator|=
name|bibtex
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
expr_stmt|;
block|}
name|host
operator|.
name|issuance
operator|=
literal|"continuing"
expr_stmt|;
if|if
condition|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|host
operator|.
name|pages
operator|=
operator|new
name|PageNumbers
argument_list|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|populateExtensionFields
argument_list|(
name|bibtex
argument_list|)
expr_stmt|;
block|}
DECL|method|populateExtensionFields (BibtexEntry e)
specifier|private
name|void
name|populateExtensionFields
parameter_list|(
name|BibtexEntry
name|e
parameter_list|)
block|{
for|for
control|(
name|String
name|field
range|:
name|e
operator|.
name|getAllFields
argument_list|()
control|)
block|{
name|String
name|value
init|=
name|e
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|field
operator|=
name|MODSEntry
operator|.
name|BIBTEX
operator|+
name|field
expr_stmt|;
name|extensionFields
operator|.
name|put
argument_list|(
name|field
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getAuthors (String authors)
specifier|private
name|List
argument_list|<
name|PersonName
argument_list|>
name|getAuthors
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
name|List
argument_list|<
name|PersonName
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<
name|PersonName
argument_list|>
argument_list|()
decl_stmt|;
name|LayoutFormatter
name|chars
init|=
operator|new
name|XMLChars
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|authors
operator|.
name|contains
argument_list|(
literal|" and "
argument_list|)
condition|)
block|{
if|if
condition|(
name|CHARFORMAT
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|PersonName
argument_list|(
name|chars
operator|.
name|format
argument_list|(
name|authors
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|PersonName
argument_list|(
name|authors
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|String
index|[]
name|names
init|=
name|authors
operator|.
name|split
argument_list|(
literal|" and "
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|name
range|:
name|names
control|)
block|{
if|if
condition|(
name|CHARFORMAT
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|PersonName
argument_list|(
name|chars
operator|.
name|format
argument_list|(
name|name
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|PersonName
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|result
return|;
block|}
comment|/* construct a MODS date object */
DECL|method|getDate (BibtexEntry bibtex)
specifier|private
name|String
name|getDate
parameter_list|(
name|BibtexEntry
name|bibtex
parameter_list|)
block|{
name|String
name|result
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|result
operator|+=
name|bibtex
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|bibtex
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|result
operator|+=
literal|'-'
operator|+
name|bibtex
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|// must be from http://www.loc.gov/marc/sourcecode/genre/genrelist.html
DECL|method|getMODSgenre (BibtexEntry bibtex)
specifier|private
name|String
name|getMODSgenre
parameter_list|(
name|BibtexEntry
name|bibtex
parameter_list|)
block|{
comment|/**          *<pre> String result; if (bibtexType.equals("Mastersthesis")) result =          * "theses"; else result = "conference publication"; // etc...</pre>          */
return|return
name|bibtex
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
return|;
block|}
DECL|method|getDOMrepresentation ()
specifier|private
name|Node
name|getDOMrepresentation
parameter_list|()
block|{
name|Node
name|result
init|=
literal|null
decl_stmt|;
try|try
block|{
name|DocumentBuilder
name|d
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
name|getDOMrepresentation
argument_list|(
name|d
operator|.
name|newDocument
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Error
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return
name|result
return|;
block|}
DECL|method|getDOMrepresentation (Document d)
specifier|public
name|Element
name|getDOMrepresentation
parameter_list|(
name|Document
name|d
parameter_list|)
block|{
try|try
block|{
name|Element
name|mods
init|=
name|d
operator|.
name|createElement
argument_list|(
name|entryType
argument_list|)
decl_stmt|;
name|mods
operator|.
name|setAttribute
argument_list|(
literal|"version"
argument_list|,
literal|"3.0"
argument_list|)
expr_stmt|;
comment|// mods.setAttribute("xmlns:xlink:", "http://www.w3.org/1999/xlink");
comment|// title
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
block|{
name|Element
name|titleInfo
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"titleInfo"
argument_list|)
decl_stmt|;
name|Element
name|mainTitle
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"title"
argument_list|)
decl_stmt|;
name|mainTitle
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|title
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|titleInfo
operator|.
name|appendChild
argument_list|(
name|mainTitle
argument_list|)
expr_stmt|;
name|mods
operator|.
name|appendChild
argument_list|(
name|titleInfo
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|authors
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|PersonName
name|name
range|:
name|authors
control|)
block|{
name|Element
name|modsName
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
name|modsName
operator|.
name|setAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"personal"
argument_list|)
expr_stmt|;
if|if
condition|(
name|name
operator|.
name|getSurname
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Element
name|namePart
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"namePart"
argument_list|)
decl_stmt|;
name|namePart
operator|.
name|setAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"family"
argument_list|)
expr_stmt|;
name|namePart
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|name
operator|.
name|getSurname
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|modsName
operator|.
name|appendChild
argument_list|(
name|namePart
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|name
operator|.
name|getGivenNames
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Element
name|namePart
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"namePart"
argument_list|)
decl_stmt|;
name|namePart
operator|.
name|setAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"given"
argument_list|)
expr_stmt|;
name|namePart
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|name
operator|.
name|getGivenNames
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|modsName
operator|.
name|appendChild
argument_list|(
name|namePart
argument_list|)
expr_stmt|;
block|}
name|Element
name|role
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"role"
argument_list|)
decl_stmt|;
name|Element
name|roleTerm
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"roleTerm"
argument_list|)
decl_stmt|;
name|roleTerm
operator|.
name|setAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"text"
argument_list|)
expr_stmt|;
name|roleTerm
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|role
operator|.
name|appendChild
argument_list|(
name|roleTerm
argument_list|)
expr_stmt|;
name|modsName
operator|.
name|appendChild
argument_list|(
name|role
argument_list|)
expr_stmt|;
name|mods
operator|.
name|appendChild
argument_list|(
name|modsName
argument_list|)
expr_stmt|;
block|}
block|}
comment|//publisher
name|Element
name|originInfo
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"originInfo"
argument_list|)
decl_stmt|;
name|mods
operator|.
name|appendChild
argument_list|(
name|originInfo
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|publisher
operator|!=
literal|null
condition|)
block|{
name|Element
name|publisher
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"publisher"
argument_list|)
decl_stmt|;
name|publisher
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|this
operator|.
name|publisher
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|originInfo
operator|.
name|appendChild
argument_list|(
name|publisher
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|date
operator|!=
literal|null
condition|)
block|{
name|Element
name|dateIssued
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"dateIssued"
argument_list|)
decl_stmt|;
name|dateIssued
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|originInfo
operator|.
name|appendChild
argument_list|(
name|dateIssued
argument_list|)
expr_stmt|;
block|}
name|Element
name|issuance
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"issuance"
argument_list|)
decl_stmt|;
name|issuance
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|this
operator|.
name|issuance
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|originInfo
operator|.
name|appendChild
argument_list|(
name|issuance
argument_list|)
expr_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
block|{
name|Element
name|idref
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"identifier"
argument_list|)
decl_stmt|;
name|idref
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|id
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|mods
operator|.
name|appendChild
argument_list|(
name|idref
argument_list|)
expr_stmt|;
name|mods
operator|.
name|setAttribute
argument_list|(
literal|"ID"
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
name|Element
name|typeOfResource
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"typeOfResource"
argument_list|)
decl_stmt|;
name|String
name|type
init|=
literal|"text"
decl_stmt|;
name|typeOfResource
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|type
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|mods
operator|.
name|appendChild
argument_list|(
name|typeOfResource
argument_list|)
expr_stmt|;
if|if
condition|(
name|genre
operator|!=
literal|null
condition|)
block|{
name|Element
name|genreElement
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"genre"
argument_list|)
decl_stmt|;
name|genreElement
operator|.
name|setAttribute
argument_list|(
literal|"authority"
argument_list|,
literal|"marc"
argument_list|)
expr_stmt|;
name|genreElement
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|genre
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|mods
operator|.
name|appendChild
argument_list|(
name|genreElement
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|host
operator|!=
literal|null
condition|)
block|{
name|Element
name|relatedItem
init|=
name|host
operator|.
name|getDOMrepresentation
argument_list|(
name|d
argument_list|)
decl_stmt|;
name|relatedItem
operator|.
name|setAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"host"
argument_list|)
expr_stmt|;
name|mods
operator|.
name|appendChild
argument_list|(
name|relatedItem
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|pages
operator|!=
literal|null
condition|)
block|{
name|mods
operator|.
name|appendChild
argument_list|(
name|pages
operator|.
name|getDOMrepresentation
argument_list|(
name|d
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/* now generate extension fields for unhandled data */
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|theEntry
range|:
name|extensionFields
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|Element
name|extension
init|=
name|d
operator|.
name|createElement
argument_list|(
literal|"extension"
argument_list|)
decl_stmt|;
name|String
name|field
init|=
name|theEntry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|theEntry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|handledExtensions
operator|.
name|contains
argument_list|(
name|field
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|Element
name|theData
init|=
name|d
operator|.
name|createElement
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|theData
operator|.
name|appendChild
argument_list|(
name|d
operator|.
name|createTextNode
argument_list|(
name|stripNonValidXMLCharacters
argument_list|(
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|extension
operator|.
name|appendChild
argument_list|(
name|theData
argument_list|)
expr_stmt|;
name|mods
operator|.
name|appendChild
argument_list|(
name|extension
argument_list|)
expr_stmt|;
block|}
return|return
name|mods
return|;
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
throw|throw
operator|new
name|Error
argument_list|(
name|e
argument_list|)
throw|;
block|}
comment|// return result;
block|}
comment|/**      * This method ensures that the output String has only      * valid XML unicode characters as specified by the      * XML 1.0 standard. For reference, please see      *<a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the      * standard</a>. This method will return an empty      * String if the input is null or empty.      *       * URL: http://cse-mjmcl.cse.bris.ac.uk/blog/2007/02/14/1171465494443.html      *      * @param in The String whose non-valid characters we want to remove.      * @return The in String, stripped of non-valid characters.      */
DECL|method|stripNonValidXMLCharacters (String in)
specifier|private
name|String
name|stripNonValidXMLCharacters
parameter_list|(
name|String
name|in
parameter_list|)
block|{
name|StringBuffer
name|out
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
comment|// Used to hold the output.
name|char
name|current
decl_stmt|;
comment|// Used to reference the current character.
if|if
condition|(
name|in
operator|==
literal|null
operator|||
name|in
operator|!=
literal|null
operator|&&
name|in
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|""
return|;
comment|// vacancy test.
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|in
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|current
operator|=
name|in
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// NOTE: No IndexOutOfBoundsException caught here; it should not happen.
if|if
condition|(
name|current
operator|==
literal|0x9
operator|||
name|current
operator|==
literal|0xA
operator|||
name|current
operator|==
literal|0xD
operator|||
name|current
operator|>=
literal|0x20
operator|&&
name|current
operator|<=
literal|0xD7FF
operator|||
name|current
operator|>=
literal|0xE000
operator|&&
name|current
operator|<=
literal|0xFFFD
operator|||
name|current
operator|>=
literal|0x10000
operator|&&
name|current
operator|<=
literal|0x10FFFF
condition|)
block|{
name|out
operator|.
name|append
argument_list|(
name|current
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|out
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/*      * render as XML      *       */
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringWriter
name|sresult
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
try|try
block|{
name|DOMSource
name|source
init|=
operator|new
name|DOMSource
argument_list|(
name|getDOMrepresentation
argument_list|()
argument_list|)
decl_stmt|;
name|StreamResult
name|result
init|=
operator|new
name|StreamResult
argument_list|(
name|sresult
argument_list|)
decl_stmt|;
name|Transformer
name|trans
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newTransformer
argument_list|()
decl_stmt|;
name|trans
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|INDENT
argument_list|,
literal|"yes"
argument_list|)
expr_stmt|;
name|trans
operator|.
name|transform
argument_list|(
name|source
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Error
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return
name|sresult
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

