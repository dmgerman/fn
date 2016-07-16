begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|Writer
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
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|BibDatabaseContext
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
name|MetaData
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
name|bibtex
operator|.
name|BibEntryWriter
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
name|bibtex
operator|.
name|LatexFieldFormatter
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
name|strings
operator|.
name|StringUtil
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
name|database
operator|.
name|BibDatabaseMode
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
name|BibtexString
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
name|CustomEntryType
import|;
end_import

begin_class
DECL|class|BibtexDatabaseWriter
specifier|public
class|class
name|BibtexDatabaseWriter
parameter_list|<
name|E
extends|extends
name|SaveSession
parameter_list|>
extends|extends
name|BibDatabaseWriter
argument_list|<
name|E
argument_list|>
block|{
DECL|field|STRING_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|STRING_PREFIX
init|=
literal|"@String"
decl_stmt|;
DECL|field|COMMENT_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|COMMENT_PREFIX
init|=
literal|"@Comment"
decl_stmt|;
DECL|field|PREAMBLE_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|PREAMBLE_PREFIX
init|=
literal|"@Preamble"
decl_stmt|;
DECL|method|BibtexDatabaseWriter (SaveSessionFactory<E> saveSessionFactory)
specifier|public
name|BibtexDatabaseWriter
parameter_list|(
name|SaveSessionFactory
argument_list|<
name|E
argument_list|>
name|saveSessionFactory
parameter_list|)
block|{
name|super
argument_list|(
name|saveSessionFactory
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|writeEpilogue (String epilogue)
specifier|protected
name|void
name|writeEpilogue
parameter_list|(
name|String
name|epilogue
parameter_list|)
throws|throws
name|SaveException
block|{
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|epilogue
argument_list|)
condition|)
block|{
try|try
block|{
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|epilogue
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|writeMetaDataItem (Map.Entry<String, String> metaItem)
specifier|protected
name|void
name|writeMetaDataItem
parameter_list|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|metaItem
parameter_list|)
throws|throws
name|SaveException
block|{
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|COMMENT_PREFIX
operator|+
literal|"{"
argument_list|)
operator|.
name|append
argument_list|(
name|MetaData
operator|.
name|META_FLAG
argument_list|)
operator|.
name|append
argument_list|(
name|metaItem
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|":"
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|metaItem
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
try|try
block|{
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|stringBuilder
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
DECL|method|writePreamble (String preamble)
specifier|protected
name|void
name|writePreamble
parameter_list|(
name|String
name|preamble
parameter_list|)
throws|throws
name|SaveException
block|{
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|preamble
argument_list|)
condition|)
block|{
try|try
block|{
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|PREAMBLE_PREFIX
operator|+
literal|"{"
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|preamble
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
literal|'}'
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|writeString (BibtexString bibtexString, boolean isFirstString, int maxKeyLength, Boolean reformatFile)
specifier|protected
name|void
name|writeString
parameter_list|(
name|BibtexString
name|bibtexString
parameter_list|,
name|boolean
name|isFirstString
parameter_list|,
name|int
name|maxKeyLength
parameter_list|,
name|Boolean
name|reformatFile
parameter_list|)
throws|throws
name|SaveException
block|{
try|try
block|{
comment|// If the string has not been modified, write it back as it was
if|if
condition|(
operator|!
name|reformatFile
operator|&&
operator|!
name|bibtexString
operator|.
name|hasChanged
argument_list|()
condition|)
block|{
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|bibtexString
operator|.
name|getParsedSerialization
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// Write user comments
name|String
name|userComments
init|=
name|bibtexString
operator|.
name|getUserComments
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|userComments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|userComments
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isFirstString
condition|)
block|{
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|STRING_PREFIX
operator|+
literal|"{"
operator|+
name|bibtexString
operator|.
name|getName
argument_list|()
operator|+
name|StringUtil
operator|.
name|repeatSpaces
argument_list|(
name|maxKeyLength
operator|-
name|bibtexString
operator|.
name|getName
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
operator|+
literal|" = "
argument_list|)
expr_stmt|;
if|if
condition|(
name|bibtexString
operator|.
name|getContent
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
literal|"{}"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|String
name|formatted
init|=
operator|new
name|LatexFieldFormatter
argument_list|()
operator|.
name|format
argument_list|(
name|bibtexString
operator|.
name|getContent
argument_list|()
argument_list|,
name|LatexFieldFormatter
operator|.
name|BIBTEX_STRING
argument_list|)
decl_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|formatted
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The # character is not allowed in BibTeX strings unless escaped as in '\\#'.\n"
operator|+
literal|"Before saving, please edit any strings containing the # character."
argument_list|)
throw|;
block|}
block|}
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
DECL|method|writeEntryTypeDefinition (CustomEntryType customType)
specifier|protected
name|void
name|writeEntryTypeDefinition
parameter_list|(
name|CustomEntryType
name|customType
parameter_list|)
throws|throws
name|SaveException
block|{
try|try
block|{
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|COMMENT_PREFIX
operator|+
literal|"{"
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|customType
operator|.
name|getAsString
argument_list|()
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
DECL|method|writePrelogue (BibDatabaseContext bibDatabaseContext, Charset encoding)
specifier|protected
name|void
name|writePrelogue
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|SaveException
block|{
if|if
condition|(
name|encoding
operator|==
literal|null
condition|)
block|{
return|return;
block|}
comment|// Writes the file encoding information.
try|try
block|{
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
literal|"% "
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|ENCODING_PREFIX
operator|+
name|encoding
argument_list|)
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
DECL|method|writeEntry (BibEntry entry, BibDatabaseMode mode, Boolean isReformatFile)
specifier|protected
name|void
name|writeEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|,
name|Boolean
name|isReformatFile
parameter_list|)
throws|throws
name|SaveException
block|{
name|BibEntryWriter
name|bibtexEntryWriter
init|=
operator|new
name|BibEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
try|try
block|{
name|bibtexEntryWriter
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|getWriter
argument_list|()
argument_list|,
name|mode
argument_list|,
name|isReformatFile
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|,
name|entry
argument_list|)
throw|;
block|}
block|}
DECL|method|getWriter ()
specifier|private
name|Writer
name|getWriter
parameter_list|()
block|{
return|return
name|getActiveSession
argument_list|()
operator|.
name|getWriter
argument_list|()
return|;
block|}
block|}
end_class

end_unit

