begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|model
operator|.
name|entry
operator|.
name|IdGenerator
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
name|xml
operator|.
name|sax
operator|.
name|Attributes
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
name|helpers
operator|.
name|DefaultHandler
import|;
end_import

begin_comment
comment|/**  * Reader for the BibTeXML format. See  *<a href="http://bibtexml.sourceforge.net/">bibtexml.sf.net</a>.  *  * @author Egon Willighagen  */
end_comment

begin_class
DECL|class|BibTeXMLHandler
class|class
name|BibTeXMLHandler
extends|extends
name|DefaultHandler
block|{
DECL|field|bibitems
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibitems
decl_stmt|;
DECL|field|b
specifier|private
name|BibEntry
name|b
decl_stmt|;
comment|// the entry being read
comment|// XML parsing stuff
DECL|field|currentChars
specifier|private
name|String
name|currentChars
decl_stmt|;
DECL|method|BibTeXMLHandler ()
specifier|public
name|BibTeXMLHandler
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|getItems ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getItems
parameter_list|()
block|{
return|return
name|bibitems
return|;
block|}
comment|// SAX parsing methods
annotation|@
name|Override
DECL|method|startDocument ()
specifier|public
name|void
name|startDocument
parameter_list|()
block|{
name|bibitems
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|endDocument ()
specifier|public
name|void
name|endDocument
parameter_list|()
block|{
comment|// Empty method
block|}
annotation|@
name|Override
DECL|method|characters (char[] ch, int start, int length)
specifier|public
name|void
name|characters
parameter_list|(
name|char
index|[]
name|ch
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|length
parameter_list|)
block|{
name|String
name|s
init|=
operator|new
name|String
argument_list|(
name|ch
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|currentChars
operator|+=
name|s
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|startElement (String uri, String local, String raw, Attributes atts)
specifier|public
name|void
name|startElement
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|local
parameter_list|,
name|String
name|raw
parameter_list|,
name|Attributes
name|atts
parameter_list|)
block|{
if|if
condition|(
literal|"bibtex:entry"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
condition|)
block|{
name|String
name|articleID
init|=
literal|null
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
name|atts
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
literal|"bibtex:id"
operator|.
name|equals
argument_list|(
name|atts
operator|.
name|getQName
argument_list|(
name|i
argument_list|)
argument_list|)
operator|||
literal|"id"
operator|.
name|equals
argument_list|(
name|atts
operator|.
name|getQName
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
block|{
name|articleID
operator|=
name|atts
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
name|b
operator|=
operator|new
name|BibEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
name|articleID
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"bibtex:article"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:inbook"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:book"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:booklet"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:incollection"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:inproceedings"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:proceedings"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:manual"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:mastersthesis"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:phdthesis"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:techreport"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:unpublished"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:misc"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
operator|||
literal|"bibtex:other"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
condition|)
block|{
name|b
operator|.
name|setType
argument_list|(
name|local
argument_list|)
expr_stmt|;
block|}
name|currentChars
operator|=
literal|""
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|endElement (String uri, String local, String raw)
specifier|public
name|void
name|endElement
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|local
parameter_list|,
name|String
name|raw
parameter_list|)
block|{
if|if
condition|(
literal|"bibtex:entry"
operator|.
name|equals
argument_list|(
name|raw
argument_list|)
condition|)
block|{
name|bibitems
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|raw
operator|.
name|startsWith
argument_list|(
literal|"bibtex:"
argument_list|)
condition|)
block|{
name|b
operator|.
name|setField
argument_list|(
name|local
argument_list|,
name|currentChars
argument_list|)
expr_stmt|;
comment|// Util.pr(local+ " "+currentChars);
block|}
name|currentChars
operator|=
literal|""
expr_stmt|;
block|}
block|}
end_class

end_unit

