begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.msbib
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|msbib
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
name|io
operator|.
name|InputStream
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
name|parsers
operator|.
name|ParserConfigurationException
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
name|BibtexDatabase
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

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_class
DECL|class|MSBibDatabase
specifier|public
class|class
name|MSBibDatabase
block|{
DECL|field|entries
specifier|private
name|Set
argument_list|<
name|MSBibEntry
argument_list|>
name|entries
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
name|MSBibDatabase
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|MSBibDatabase ()
specifier|public
name|MSBibDatabase
parameter_list|()
block|{
comment|// maybe make this sorted later...
name|entries
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
expr_stmt|;
block|}
DECL|method|MSBibDatabase (InputStream stream)
specifier|public
name|MSBibDatabase
parameter_list|(
name|InputStream
name|stream
parameter_list|)
block|{
name|importEntries
argument_list|(
name|stream
argument_list|)
expr_stmt|;
block|}
DECL|method|MSBibDatabase (BibtexDatabase bibtex)
specifier|public
name|MSBibDatabase
parameter_list|(
name|BibtexDatabase
name|bibtex
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
init|=
name|bibtex
operator|.
name|getKeySet
argument_list|()
decl_stmt|;
name|addEntries
argument_list|(
name|bibtex
argument_list|,
name|keySet
argument_list|)
expr_stmt|;
block|}
DECL|method|MSBibDatabase (BibtexDatabase bibtex, Set<String> keySet)
specifier|public
name|MSBibDatabase
parameter_list|(
name|BibtexDatabase
name|bibtex
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
parameter_list|)
block|{
if|if
condition|(
name|keySet
operator|==
literal|null
condition|)
block|{
name|keySet
operator|=
name|bibtex
operator|.
name|getKeySet
argument_list|()
expr_stmt|;
block|}
name|addEntries
argument_list|(
name|bibtex
argument_list|,
name|keySet
argument_list|)
expr_stmt|;
block|}
DECL|method|importEntries (InputStream stream)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|stream
parameter_list|)
block|{
name|entries
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
expr_stmt|;
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|Document
name|inputDocument
init|=
literal|null
decl_stmt|;
try|try
block|{
name|DocumentBuilder
name|documentBuilder
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|inputDocument
operator|=
name|documentBuilder
operator|.
name|parse
argument_list|(
name|stream
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
decl||
name|SAXException
decl||
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not parse document"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|String
name|bcol
init|=
literal|"b:"
decl_stmt|;
name|NodeList
name|rootList
init|=
name|inputDocument
operator|.
name|getElementsByTagName
argument_list|(
literal|"b:Sources"
argument_list|)
decl_stmt|;
if|if
condition|(
name|rootList
operator|.
name|getLength
argument_list|()
operator|==
literal|0
condition|)
block|{
name|rootList
operator|=
name|inputDocument
operator|.
name|getElementsByTagName
argument_list|(
literal|"Sources"
argument_list|)
expr_stmt|;
name|bcol
operator|=
literal|""
expr_stmt|;
block|}
if|if
condition|(
name|rootList
operator|.
name|getLength
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return
name|bibitems
return|;
block|}
name|NodeList
name|sourceList
init|=
operator|(
operator|(
name|Element
operator|)
name|rootList
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|getElementsByTagName
argument_list|(
name|bcol
operator|+
literal|"Source"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sourceList
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|MSBibEntry
name|entry
init|=
operator|new
name|MSBibEntry
argument_list|(
operator|(
name|Element
operator|)
name|sourceList
operator|.
name|item
argument_list|(
name|i
argument_list|)
argument_list|,
name|bcol
argument_list|)
decl_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|bibitems
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getBibtexRepresentation
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|bibitems
return|;
block|}
DECL|method|addEntries (BibtexDatabase database, Set<String> keySet)
specifier|private
name|void
name|addEntries
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
name|entries
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
expr_stmt|;
for|for
control|(
name|String
name|key
range|:
name|keySet
control|)
block|{
name|BibtexEntry
name|entry
init|=
name|database
operator|.
name|getEntryById
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|MSBibEntry
name|newMods
init|=
operator|new
name|MSBibEntry
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|newMods
argument_list|)
expr_stmt|;
block|}
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
name|documentBuilder
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
name|documentBuilder
operator|.
name|newDocument
argument_list|()
expr_stmt|;
name|Element
name|msbibCollection
init|=
name|result
operator|.
name|createElement
argument_list|(
literal|"b:Sources"
argument_list|)
decl_stmt|;
name|msbibCollection
operator|.
name|setAttribute
argument_list|(
literal|"SelectedStyle"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|msbibCollection
operator|.
name|setAttribute
argument_list|(
literal|"xmlns"
argument_list|,
literal|"http://schemas.openxmlformats.org/officeDocument/2006/bibliography"
argument_list|)
expr_stmt|;
name|msbibCollection
operator|.
name|setAttribute
argument_list|(
literal|"xmlns:b"
argument_list|,
literal|"http://schemas.openxmlformats.org/officeDocument/2006/bibliography"
argument_list|)
expr_stmt|;
for|for
control|(
name|MSBibEntry
name|entry
range|:
name|entries
control|)
block|{
name|Node
name|node
init|=
name|entry
operator|.
name|getDOMrepresentation
argument_list|(
name|result
argument_list|)
decl_stmt|;
name|msbibCollection
operator|.
name|appendChild
argument_list|(
name|node
argument_list|)
expr_stmt|;
block|}
name|result
operator|.
name|appendChild
argument_list|(
name|msbibCollection
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not build document"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

