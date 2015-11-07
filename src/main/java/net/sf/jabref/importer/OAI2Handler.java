begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
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
name|SAXException
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
comment|/**  * SAX-Handler to parse OAI2-xml files.  *   * @author Ulrich St&auml;rk  * @author Christian Kopf  * @author Christopher Oezbek  */
end_comment

begin_class
DECL|class|OAI2Handler
specifier|public
class|class
name|OAI2Handler
extends|extends
name|DefaultHandler
block|{
DECL|field|entry
specifier|private
specifier|final
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|authors
specifier|private
name|StringBuffer
name|authors
decl_stmt|;
DECL|field|keyname
specifier|private
name|String
name|keyname
decl_stmt|;
DECL|field|forenames
specifier|private
name|String
name|forenames
decl_stmt|;
DECL|field|characters
specifier|private
name|StringBuffer
name|characters
decl_stmt|;
DECL|method|OAI2Handler (BibtexEntry be)
specifier|public
name|OAI2Handler
parameter_list|(
name|BibtexEntry
name|be
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|be
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|startDocument ()
specifier|public
name|void
name|startDocument
parameter_list|()
throws|throws
name|SAXException
block|{
name|authors
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
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
throws|throws
name|SAXException
block|{
name|characters
operator|.
name|append
argument_list|(
name|ch
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|startElement (String uri, String localName, String qualifiedName, Attributes attributes)
specifier|public
name|void
name|startElement
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|localName
parameter_list|,
name|String
name|qualifiedName
parameter_list|,
name|Attributes
name|attributes
parameter_list|)
throws|throws
name|SAXException
block|{
name|characters
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|endElement (String uri, String localName, String qualifiedName)
specifier|public
name|void
name|endElement
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|localName
parameter_list|,
name|String
name|qualifiedName
parameter_list|)
throws|throws
name|SAXException
block|{
name|String
name|content
init|=
name|characters
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"error"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|content
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"id"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"eprint"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"keyname"
argument_list|)
condition|)
block|{
name|keyname
operator|=
name|content
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"forenames"
argument_list|)
condition|)
block|{
name|forenames
operator|=
name|content
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"journal-ref"
argument_list|)
condition|)
block|{
name|String
name|journal
init|=
name|content
operator|.
name|replaceFirst
argument_list|(
literal|"[0-9].*"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
name|journal
argument_list|)
expr_stmt|;
name|String
name|volume
init|=
name|content
operator|.
name|replaceFirst
argument_list|(
name|journal
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|volume
operator|=
name|volume
operator|.
name|replaceFirst
argument_list|(
literal|" .*"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
name|volume
argument_list|)
expr_stmt|;
name|String
name|year
init|=
name|content
operator|.
name|replaceFirst
argument_list|(
literal|".*?\\("
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|year
operator|=
name|year
operator|.
name|replaceFirst
argument_list|(
literal|"\\).*"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
name|year
argument_list|)
expr_stmt|;
name|String
name|pages
init|=
name|content
operator|.
name|replaceFirst
argument_list|(
name|journal
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|pages
operator|=
name|pages
operator|.
name|replaceFirst
argument_list|(
name|volume
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|pages
operator|=
name|pages
operator|.
name|replaceFirst
argument_list|(
literal|"\\("
operator|+
name|year
operator|+
literal|"\\)"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|pages
operator|=
name|pages
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
name|pages
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"datestamp"
argument_list|)
condition|)
block|{
name|String
name|year
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
if|if
condition|(
name|year
operator|==
literal|null
operator|||
name|year
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
name|content
operator|.
name|replaceFirst
argument_list|(
literal|"-.*"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"title"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"abstract"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"comments"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"comments"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"report-no"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"reportno"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"doi"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|qualifiedName
operator|.
name|equals
argument_list|(
literal|"author"
argument_list|)
condition|)
block|{
name|String
name|author
init|=
name|forenames
operator|+
literal|" "
operator|+
name|keyname
decl_stmt|;
if|if
condition|(
name|authors
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|authors
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
name|authors
operator|.
name|append
argument_list|(
name|author
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|endDocument ()
specifier|public
name|void
name|endDocument
parameter_list|()
throws|throws
name|SAXException
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|authors
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

