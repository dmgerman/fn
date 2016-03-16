begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|logic
operator|.
name|layout
operator|.
name|Layout
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
name|layout
operator|.
name|LayoutHelper
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
name|BibDatabase
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
name|javax
operator|.
name|swing
operator|.
name|filechooser
operator|.
name|FileFilter
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
name|io
operator|.
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileReader
import|;
end_import

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
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
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
name|*
import|;
end_import

begin_comment
comment|/**  * Base class for export formats based on templates.  */
end_comment

begin_class
DECL|class|ExportFormat
specifier|public
class|class
name|ExportFormat
implements|implements
name|IExportFormat
block|{
DECL|field|displayName
specifier|private
name|String
name|displayName
decl_stmt|;
DECL|field|consoleName
specifier|private
name|String
name|consoleName
decl_stmt|;
DECL|field|lfFileName
specifier|private
name|String
name|lfFileName
decl_stmt|;
DECL|field|directory
specifier|private
name|String
name|directory
decl_stmt|;
DECL|field|extension
specifier|private
name|String
name|extension
decl_stmt|;
DECL|field|encoding
specifier|private
name|Charset
name|encoding
decl_stmt|;
comment|// If this value is set, it will be used to override
comment|// the default encoding for the getCurrentBasePanel.
DECL|field|fileFilter
specifier|private
name|FileFilter
name|fileFilter
decl_stmt|;
DECL|field|customExport
specifier|private
name|boolean
name|customExport
decl_stmt|;
DECL|field|LAYOUT_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|LAYOUT_PREFIX
init|=
literal|"/resource/layout/"
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
name|ExportFormat
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Initialize another export format based on templates stored in dir with      * layoutFile lfFilename.      *      * @param displayName Name to display to the user.      * @param consoleName Name to call this format in the console.      * @param lfFileName  Name of the main layout file.      * @param directory   Directory in which to find the layout file.      * @param extension   Should contain the . (for instance .txt).      */
DECL|method|ExportFormat (String displayName, String consoleName, String lfFileName, String directory, String extension)
specifier|public
name|ExportFormat
parameter_list|(
name|String
name|displayName
parameter_list|,
name|String
name|consoleName
parameter_list|,
name|String
name|lfFileName
parameter_list|,
name|String
name|directory
parameter_list|,
name|String
name|extension
parameter_list|)
block|{
name|this
operator|.
name|displayName
operator|=
name|displayName
expr_stmt|;
name|this
operator|.
name|consoleName
operator|=
name|consoleName
expr_stmt|;
name|this
operator|.
name|lfFileName
operator|=
name|lfFileName
expr_stmt|;
name|this
operator|.
name|directory
operator|=
name|directory
expr_stmt|;
name|this
operator|.
name|extension
operator|=
name|extension
expr_stmt|;
block|}
comment|/**      * Empty default constructor for subclasses      */
DECL|method|ExportFormat ()
name|ExportFormat
parameter_list|()
block|{
comment|// intentionally empty
block|}
comment|/**      * Indicate whether this is a custom export. A custom export looks for its      * layout files using a normal file path, while a built-in export looks in      * the classpath.      *      * @param custom true to indicate a custom export format.      */
DECL|method|setCustomExport (boolean custom)
specifier|public
name|void
name|setCustomExport
parameter_list|(
name|boolean
name|custom
parameter_list|)
block|{
name|this
operator|.
name|customExport
operator|=
name|custom
expr_stmt|;
block|}
comment|/**      * @see IExportFormat#getConsoleName()      */
annotation|@
name|Override
DECL|method|getConsoleName ()
specifier|public
name|String
name|getConsoleName
parameter_list|()
block|{
return|return
name|consoleName
return|;
block|}
comment|/**      * @see IExportFormat#getDisplayName()      */
annotation|@
name|Override
DECL|method|getDisplayName ()
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|displayName
return|;
block|}
comment|/**      * Set an encoding which will be used in preference to the default value      * obtained from the basepanel.      *      * @param encoding The name of the encoding to use.      */
DECL|method|setEncoding (Charset encoding)
specifier|public
name|void
name|setEncoding
parameter_list|(
name|Charset
name|encoding
parameter_list|)
block|{
name|this
operator|.
name|encoding
operator|=
name|encoding
expr_stmt|;
block|}
comment|/**      * This method should return a reader from which the given layout file can      * be read.      *<p>      *<p>      * Subclasses of ExportFormat are free to override and provide their own      * implementation.      *      * @param filename the filename      * @return a newly created reader      * @throws IOException if the reader could not be created      */
DECL|method|getReader (String filename)
specifier|private
name|Reader
name|getReader
parameter_list|(
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
comment|// If this is a custom export, just use the given filename:
name|String
name|dir
decl_stmt|;
if|if
condition|(
name|customExport
condition|)
block|{
name|dir
operator|=
literal|""
expr_stmt|;
block|}
else|else
block|{
name|dir
operator|=
name|LAYOUT_PREFIX
operator|+
operator|(
name|directory
operator|==
literal|null
condition|?
literal|""
else|:
name|directory
operator|+
literal|'/'
operator|)
expr_stmt|;
block|}
comment|// Attempt to get a Reader for the file path given, either by
comment|// loading it as a resource (from within jar), or as a normal file. If
comment|// unsuccessful (e.g. file not found), an IOException is thrown.
name|String
name|name
init|=
name|dir
operator|+
name|filename
decl_stmt|;
name|Reader
name|reader
decl_stmt|;
comment|// Try loading as a resource first. This works for files inside the jar:
name|URL
name|reso
init|=
name|Globals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|// If that didn't work, try loading as a normal file URL:
try|try
block|{
if|if
condition|(
name|reso
operator|==
literal|null
condition|)
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|reader
operator|=
operator|new
name|FileReader
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|reso
operator|.
name|openStream
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Cannot find layout file: '"
operator|+
name|name
operator|+
literal|"'."
argument_list|)
throw|;
block|}
return|return
name|reader
return|;
block|}
comment|/**      * Perform the export of {@code database}.      *      * @param database   The database to export from.      * @param metaData   The database's meta data.      * @param file       the file to write the resulting export to      * @param encoding   The encoding of the database      * @param entries    Contains all entries that should be exported. If      *<code>null</code>, all entries will be exported.      * @throws IOException if a problem occurred while trying to write to {@code writer}      *                     or read from required resources.      * @throws Exception   if any other error occurred during export.      * @see net.sf.jabref.exporter.IExportFormat#performExport(BibDatabase, MetaData, String, Charset, List)      */
annotation|@
name|Override
DECL|method|performExport (final BibDatabase database, final MetaData metaData, final String file, final Charset encoding, List<BibEntry> entries)
specifier|public
name|void
name|performExport
parameter_list|(
specifier|final
name|BibDatabase
name|database
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
specifier|final
name|String
name|file
parameter_list|,
specifier|final
name|Charset
name|encoding
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
throws|throws
name|Exception
block|{
name|File
name|outFile
init|=
operator|new
name|File
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|SaveSession
name|ss
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|encoding
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|ss
operator|=
operator|new
name|SaveSession
argument_list|(
name|this
operator|.
name|encoding
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// Perhaps the overriding encoding doesn't work?
comment|// We will fall back on the default encoding.
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Can not get save session."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|ss
operator|==
literal|null
condition|)
block|{
name|ss
operator|=
operator|new
name|SaveSession
argument_list|(
name|encoding
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
try|try
init|(
name|VerifyingWriter
name|ps
init|=
name|ss
operator|.
name|getWriter
argument_list|()
init|)
block|{
name|Layout
name|beginLayout
init|=
literal|null
decl_stmt|;
comment|// Check if this export filter has bundled name formatters:
comment|// Set a global field, so all layouts have access to the custom name formatters:
name|Globals
operator|.
name|prefs
operator|.
name|customExportNameFormatters
operator|=
name|readFormatterFile
argument_list|(
name|lfFileName
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|missingFormatters
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
comment|// Print header
try|try
init|(
name|Reader
name|reader
init|=
name|getReader
argument_list|(
name|lfFileName
operator|+
literal|".begin.layout"
argument_list|)
init|)
block|{
name|LayoutHelper
name|layoutHelper
init|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|()
argument_list|)
decl_stmt|;
name|beginLayout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// If an exception was cast, export filter doesn't have a begin
comment|// file.
block|}
comment|// Write the header
if|if
condition|(
name|beginLayout
operator|!=
literal|null
condition|)
block|{
name|ps
operator|.
name|write
argument_list|(
name|beginLayout
operator|.
name|doLayout
argument_list|(
name|database
argument_list|,
name|encoding
argument_list|)
argument_list|)
expr_stmt|;
name|missingFormatters
operator|.
name|addAll
argument_list|(
name|beginLayout
operator|.
name|getMissingFormatters
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*              * Write database entries; entries will be sorted as they appear on the              * screen, or sorted by author, depending on Preferences. We also supply              * the Set entries - if we are to export only certain entries, it will              * be non-null, and be used to choose entries. Otherwise, it will be              * null, and be ignored.              */
name|Defaults
name|defaults
init|=
operator|new
name|Defaults
argument_list|(
name|BibDatabaseMode
operator|.
name|fromPreference
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIBLATEX_DEFAULT_MODE
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|SavePreferences
name|savePrefs
init|=
name|SavePreferences
operator|.
name|loadForExportFromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|sorted
init|=
name|BibDatabaseWriter
operator|.
name|getSortedEntries
argument_list|(
operator|new
name|BibDatabaseContext
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|defaults
argument_list|)
argument_list|,
name|entries
argument_list|,
name|savePrefs
argument_list|)
decl_stmt|;
comment|// Load default layout
name|Layout
name|defLayout
decl_stmt|;
name|LayoutHelper
name|layoutHelper
decl_stmt|;
try|try
init|(
name|Reader
name|reader
init|=
name|getReader
argument_list|(
name|lfFileName
operator|+
literal|".layout"
argument_list|)
init|)
block|{
name|layoutHelper
operator|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|()
argument_list|)
expr_stmt|;
name|defLayout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|defLayout
operator|!=
literal|null
condition|)
block|{
name|missingFormatters
operator|.
name|addAll
argument_list|(
name|defLayout
operator|.
name|getMissingFormatters
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|missingFormatters
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|missingFormatters
argument_list|)
expr_stmt|;
block|}
block|}
name|HashMap
argument_list|<
name|String
argument_list|,
name|Layout
argument_list|>
name|layouts
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|Layout
name|layout
decl_stmt|;
name|ExportFormats
operator|.
name|entryNumber
operator|=
literal|0
expr_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|sorted
control|)
block|{
name|ExportFormats
operator|.
name|entryNumber
operator|++
expr_stmt|;
comment|// Increment entry counter.
comment|// Get the layout
name|String
name|type
init|=
name|entry
operator|.
name|getType
argument_list|()
decl_stmt|;
if|if
condition|(
name|layouts
operator|.
name|containsKey
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|layout
operator|=
name|layouts
operator|.
name|get
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
init|(
name|Reader
name|reader
init|=
name|getReader
argument_list|(
name|lfFileName
operator|+
literal|'.'
operator|+
name|type
operator|+
literal|".layout"
argument_list|)
init|)
block|{
comment|// We try to get a type-specific layout for this entry.
name|layoutHelper
operator|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|()
argument_list|)
expr_stmt|;
name|layout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|()
expr_stmt|;
name|layouts
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|layout
argument_list|)
expr_stmt|;
if|if
condition|(
name|layout
operator|!=
literal|null
condition|)
block|{
name|missingFormatters
operator|.
name|addAll
argument_list|(
name|layout
operator|.
name|getMissingFormatters
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// The exception indicates that no type-specific layout
comment|// exists, so we
comment|// go with the default one.
name|layout
operator|=
name|defLayout
expr_stmt|;
block|}
block|}
comment|// Write the entry
name|ps
operator|.
name|write
argument_list|(
name|layout
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Print footer
comment|// changed section - begin (arudert)
name|Layout
name|endLayout
init|=
literal|null
decl_stmt|;
try|try
init|(
name|Reader
name|reader
init|=
name|getReader
argument_list|(
name|lfFileName
operator|+
literal|".end.layout"
argument_list|)
init|)
block|{
name|layoutHelper
operator|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|()
argument_list|)
expr_stmt|;
name|endLayout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// If an exception was thrown, export filter doesn't have an end
comment|// file.
block|}
comment|// Write footer
if|if
condition|(
operator|(
name|endLayout
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|this
operator|.
name|encoding
operator|!=
literal|null
operator|)
condition|)
block|{
name|ps
operator|.
name|write
argument_list|(
name|endLayout
operator|.
name|doLayout
argument_list|(
name|database
argument_list|,
name|this
operator|.
name|encoding
argument_list|)
argument_list|)
expr_stmt|;
name|missingFormatters
operator|.
name|addAll
argument_list|(
name|endLayout
operator|.
name|getMissingFormatters
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Clear custom name formatters:
name|Globals
operator|.
name|prefs
operator|.
name|customExportNameFormatters
operator|=
literal|null
expr_stmt|;
if|if
condition|(
operator|!
name|missingFormatters
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"The following formatters could not be found: "
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|String
operator|.
name|join
argument_list|(
literal|", "
argument_list|,
name|missingFormatters
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
name|sb
argument_list|)
expr_stmt|;
block|}
name|finalizeSaveSession
argument_list|(
name|ss
argument_list|,
name|outFile
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * See if there is a name formatter file bundled with this export format. If so, read      * all the name formatters so they can be used by the filter layouts.      *      * @param lfFileName The layout filename.      */
DECL|method|readFormatterFile (String lfFileName)
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|readFormatterFile
parameter_list|(
name|String
name|lfFileName
parameter_list|)
block|{
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|formatters
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|File
name|formatterFile
init|=
operator|new
name|File
argument_list|(
name|lfFileName
operator|+
literal|".formatters"
argument_list|)
decl_stmt|;
if|if
condition|(
name|formatterFile
operator|.
name|exists
argument_list|()
condition|)
block|{
try|try
init|(
name|Reader
name|in
init|=
operator|new
name|FileReader
argument_list|(
name|formatterFile
argument_list|)
init|)
block|{
comment|// Ok, we found and opened the file. Read all contents:
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|c
decl_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|in
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|lines
init|=
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
comment|// Go through each line:
for|for
control|(
name|String
name|line1
range|:
name|lines
control|)
block|{
name|String
name|line
init|=
name|line1
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// Do not deal with empty lines:
if|if
condition|(
name|line
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
name|int
name|index
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
decl_stmt|;
comment|// TODO: any need to accept escaped colons here?
if|if
condition|(
operator|(
name|index
operator|>
literal|0
operator|)
operator|&&
operator|(
operator|(
name|index
operator|+
literal|1
operator|)
operator|<
name|line
operator|.
name|length
argument_list|()
operator|)
condition|)
block|{
name|String
name|formatterName
init|=
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
decl_stmt|;
name|String
name|contents
init|=
name|line
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
decl_stmt|;
name|formatters
operator|.
name|put
argument_list|(
name|formatterName
argument_list|,
name|contents
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// TODO: show error message here?
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem opening formatter file."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|formatters
return|;
block|}
comment|/**      * @see net.sf.jabref.exporter.IExportFormat#getFileFilter()      */
annotation|@
name|Override
DECL|method|getFileFilter ()
specifier|public
name|FileFilter
name|getFileFilter
parameter_list|()
block|{
if|if
condition|(
name|fileFilter
operator|==
literal|null
condition|)
block|{
name|fileFilter
operator|=
operator|new
name|ExportFileFilter
argument_list|(
name|this
argument_list|,
name|extension
argument_list|)
expr_stmt|;
block|}
return|return
name|fileFilter
return|;
block|}
DECL|method|finalizeSaveSession (final SaveSession ss, File file)
specifier|public
name|void
name|finalizeSaveSession
parameter_list|(
specifier|final
name|SaveSession
name|ss
parameter_list|,
name|File
name|file
parameter_list|)
throws|throws
name|SaveException
throws|,
name|IOException
block|{
name|ss
operator|.
name|getWriter
argument_list|()
operator|.
name|flush
argument_list|()
expr_stmt|;
name|ss
operator|.
name|getWriter
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|ss
operator|.
name|getWriter
argument_list|()
operator|.
name|couldEncodeAll
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not encode..."
argument_list|)
expr_stmt|;
block|}
name|ss
operator|.
name|commit
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

