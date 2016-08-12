begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|Objects
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
name|java
operator|.
name|util
operator|.
name|SortedSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|importer
operator|.
name|fileformat
operator|.
name|BibTeXMLImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|BiblioscapeImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|CopacImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|EndnoteImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|FreeCiteImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|ImportFormat
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
name|importer
operator|.
name|fileformat
operator|.
name|InspecImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|IsiImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|MedlineImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|MedlinePlainImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|MsBibImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|OvidImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|PdfContentImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|PdfXmpImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|RepecNepImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|RisImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|SilverPlatterImporter
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
name|BibDatabases
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

begin_class
DECL|class|ImportFormatReader
specifier|public
class|class
name|ImportFormatReader
block|{
DECL|field|BIBTEX_FORMAT
specifier|public
specifier|static
specifier|final
name|String
name|BIBTEX_FORMAT
init|=
literal|"BibTeX"
decl_stmt|;
comment|/**      * All import formats.      * Sorted accordingly to {@link ImportFormat#compareTo}, which defaults to alphabetically by the name      */
DECL|field|formats
specifier|private
specifier|final
name|SortedSet
argument_list|<
name|ImportFormat
argument_list|>
name|formats
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
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
name|ImportFormatReader
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|resetImportFormats ()
specifier|public
name|void
name|resetImportFormats
parameter_list|()
block|{
name|formats
operator|.
name|clear
argument_list|()
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|BiblioscapeImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|BibtexImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|BibTeXMLImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|CopacImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|EndnoteImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|FreeCiteImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|InspecImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|IsiImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|MedlineImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|MedlinePlainImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|MsBibImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|OvidImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|PdfContentImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|PdfXmpImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|RepecNepImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|RisImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|SilverPlatterImporter
argument_list|()
argument_list|)
expr_stmt|;
comment|/**          * Get custom import formats          */
for|for
control|(
name|CustomImporter
name|importer
range|:
name|Globals
operator|.
name|prefs
operator|.
name|customImports
control|)
block|{
try|try
block|{
name|ImportFormat
name|imFo
init|=
name|importer
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|formats
operator|.
name|add
argument_list|(
name|imFo
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|ClassNotFoundException
decl||
name|InstantiationException
decl||
name|IllegalAccessException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not instantiate "
operator|+
name|importer
operator|.
name|getName
argument_list|()
operator|+
literal|" importer, will ignore it. Please check if the class is still available."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Format for a given CLI-ID.      *<p>      *<p>Will return the first format according to the default-order of      * format that matches the given ID.</p>      *      * @param cliId CLI-Id      * @return Import Format or<code>null</code> if none matches      */
DECL|method|getByCliId (String cliId)
specifier|private
name|Optional
argument_list|<
name|ImportFormat
argument_list|>
name|getByCliId
parameter_list|(
name|String
name|cliId
parameter_list|)
block|{
for|for
control|(
name|ImportFormat
name|format
range|:
name|formats
control|)
block|{
if|if
condition|(
name|format
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|cliId
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|format
argument_list|)
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|importFromFile (String format, Path file)
specifier|public
name|ParserResult
name|importFromFile
parameter_list|(
name|String
name|format
parameter_list|,
name|Path
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|Optional
argument_list|<
name|ImportFormat
argument_list|>
name|importer
init|=
name|getByCliId
argument_list|(
name|format
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|importer
operator|.
name|isPresent
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unknown import format: "
operator|+
name|format
argument_list|)
throw|;
block|}
return|return
name|importer
operator|.
name|get
argument_list|()
operator|.
name|importDatabase
argument_list|(
name|file
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * All importers.      *<p>      *<p>      * Elements are in default order.      *</p>      *      * @return all custom importers, elements are of type InputFormat      */
DECL|method|getImportFormats ()
specifier|public
name|SortedSet
argument_list|<
name|ImportFormat
argument_list|>
name|getImportFormats
parameter_list|()
block|{
return|return
name|this
operator|.
name|formats
return|;
block|}
comment|/**      * Human readable list of all known import formats (name and CLI Id).      *<p>      *<p>List is in default-order.</p>      *      * @return human readable list of all known import formats      */
DECL|method|getImportFormatList ()
specifier|public
name|String
name|getImportFormatList
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|ImportFormat
name|imFo
range|:
name|formats
control|)
block|{
name|int
name|pad
init|=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
literal|14
operator|-
name|imFo
operator|.
name|getFormatName
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"  "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|imFo
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|repeatSpaces
argument_list|(
name|pad
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" : "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|imFo
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|class|UnknownFormatImport
specifier|public
specifier|static
class|class
name|UnknownFormatImport
block|{
DECL|field|format
specifier|public
specifier|final
name|String
name|format
decl_stmt|;
DECL|field|parserResult
specifier|public
specifier|final
name|ParserResult
name|parserResult
decl_stmt|;
DECL|method|UnknownFormatImport (String format, ParserResult parserResult)
specifier|public
name|UnknownFormatImport
parameter_list|(
name|String
name|format
parameter_list|,
name|ParserResult
name|parserResult
parameter_list|)
block|{
name|this
operator|.
name|format
operator|=
name|format
expr_stmt|;
name|this
operator|.
name|parserResult
operator|=
name|parserResult
expr_stmt|;
block|}
block|}
DECL|method|importUnknownFormat (String filename)
specifier|public
name|UnknownFormatImport
name|importUnknownFormat
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
return|return
name|importUnknownFormat
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|filename
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Tries to import a file by iterating through the available import filters,      * and keeping the import that seems most promising.      *<p/>      * If all fails this method attempts to read this file as bibtex.      *      * @throws IOException      */
DECL|method|importUnknownFormat (Path file)
specifier|public
name|UnknownFormatImport
name|importUnknownFormat
parameter_list|(
name|Path
name|file
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|file
argument_list|)
expr_stmt|;
comment|// First, see if it is a BibTeX file:
try|try
block|{
name|ParserResult
name|pr
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|file
operator|.
name|toFile
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|hasEntries
argument_list|()
operator|||
operator|!
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|hasNoStrings
argument_list|()
condition|)
block|{
name|pr
operator|.
name|setFile
argument_list|(
name|file
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|new
name|UnknownFormatImport
argument_list|(
name|ImportFormatReader
operator|.
name|BIBTEX_FORMAT
argument_list|,
name|pr
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ignore
parameter_list|)
block|{
comment|// Ignored
block|}
comment|// stores ref to best result, gets updated at the next loop
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bestResult
init|=
literal|null
decl_stmt|;
name|int
name|bestResultCount
init|=
literal|0
decl_stmt|;
name|String
name|bestFormatName
init|=
literal|null
decl_stmt|;
comment|// Cycle through all importers:
for|for
control|(
name|ImportFormat
name|imFo
range|:
name|getImportFormats
argument_list|()
control|)
block|{
try|try
block|{
if|if
condition|(
operator|!
name|imFo
operator|.
name|isRecognizedFormat
argument_list|(
name|file
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|ParserResult
name|parserResult
init|=
name|imFo
operator|.
name|importDatabase
argument_list|(
name|file
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|parserResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibDatabases
operator|.
name|purgeEmptyEntries
argument_list|(
name|entries
argument_list|)
expr_stmt|;
name|int
name|entryCount
init|=
name|entries
operator|.
name|size
argument_list|()
decl_stmt|;
if|if
condition|(
name|entryCount
operator|>
name|bestResultCount
condition|)
block|{
name|bestResult
operator|=
name|entries
expr_stmt|;
name|bestResultCount
operator|=
name|bestResult
operator|.
name|size
argument_list|()
expr_stmt|;
name|bestFormatName
operator|=
name|imFo
operator|.
name|getFormatName
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// The import did not succeed. Go on.
block|}
block|}
if|if
condition|(
name|bestResult
operator|!=
literal|null
condition|)
block|{
comment|// we found something
name|ParserResult
name|parserResult
init|=
operator|new
name|ParserResult
argument_list|(
name|bestResult
argument_list|)
decl_stmt|;
return|return
operator|new
name|UnknownFormatImport
argument_list|(
name|bestFormatName
argument_list|,
name|parserResult
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

