begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|export
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
name|export
operator|.
name|layout
operator|.
name|LayoutHelper
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
name|Reader
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
comment|/**  * Base class for export formats based on templates.  *   */
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
name|String
name|displayName
decl_stmt|;
DECL|field|consoleName
name|String
name|consoleName
decl_stmt|;
DECL|field|lfFileName
name|String
name|lfFileName
decl_stmt|;
DECL|field|directory
name|String
name|directory
decl_stmt|;
DECL|field|extension
name|String
name|extension
decl_stmt|;
DECL|field|encoding
name|String
name|encoding
init|=
literal|null
decl_stmt|;
comment|// If this value is set, it will be used to override
comment|// the default encoding for the basePanel.
DECL|field|fileFilter
name|FileFilter
name|fileFilter
decl_stmt|;
DECL|field|customExport
name|boolean
name|customExport
init|=
literal|false
decl_stmt|;
comment|/** 	 * Initialize another export format based on templates stored in dir with 	 * layoutFile lfFilename. 	 *  	 * @param displayName 	 *            Name to display to the user. 	 * @param consoleName 	 *            Name to call this format in the console. 	 * @param lfFileName 	 *            Name of the main layout file. 	 * @param directory 	 *            Directory in which to find the layout file. 	 * @param extension 	 *            Should contain the . (for instance .txt). 	 */
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
comment|/** Empty default constructor for subclasses */
DECL|method|ExportFormat ()
specifier|protected
name|ExportFormat
parameter_list|()
block|{
comment|// intentionally empty
block|}
comment|/** 	 * Indicate whether this is a custom export. A custom export looks for its 	 * layout files using a normal file path, while a built-in export looks in 	 * the classpath. 	 *  	 * @param custom 	 *            true to indicate a custom export format. 	 */
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
comment|/** 	 * @see IExportFormat#getConsoleName() 	 */
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
comment|/** 	 * @see IExportFormat#getDisplayName() 	 */
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
comment|/**      * Set an encoding which will be used in preference to the default value      * obtained from the basepanel.      * @param encoding The name of the encoding to use.      */
DECL|method|setEncoding (String encoding)
specifier|protected
name|void
name|setEncoding
parameter_list|(
name|String
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
comment|/** 	 * This method should return a reader from which the given layout file can 	 * be read. 	 *  	 * This standard implementation of this method will use the 	 * {@link FileActions#getReader(String)} method. 	 *  	 * Subclasses of ExportFormat are free to override and provide their own 	 * implementation. 	 *  	 * @param filename 	 *            the file name 	 * @throws IOException 	 *             if the reader could not be created 	 *  	 * @return a newly created reader 	 */
DECL|method|getReader (String filename)
specifier|protected
name|Reader
name|getReader
parameter_list|(
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
comment|// If this is a custom export, just use the given file name:
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
name|Globals
operator|.
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
literal|"/"
operator|)
expr_stmt|;
block|}
return|return
name|FileActions
operator|.
name|getReader
argument_list|(
name|dir
operator|+
name|filename
argument_list|)
return|;
block|}
comment|/** 	 * Perform the export of {@code database}. 	 *  	 * @param database 	 *            The database to export from.      * @param metaData      *            The database's meta data. 	 * @param file 	 *            the file to write the resulting export to 	 * @param encoding 	 *            The encoding of the database 	 * @param entryIds 	 *            Contains the IDs of all entries that should be exported. If 	 *<code>null</code>, all entries will be exported. 	 *  	 * @throws IOException 	 *             if a problem occurred while trying to write to {@code writer} 	 *             or read from required resources. 	 * @throws Exception 	 *             if any other error occurred during export. 	 *  	 * @see net.sf.jabref.export.IExportFormat#performExport(net.sf.jabref.BibtexDatabase, 	 *      net.sf.jabref.MetaData, java.lang.String, java.lang.String, java.util.Set) 	 */
DECL|method|performExport (final BibtexDatabase database, final MetaData metaData, final String file, final String encoding, Set<String> entryIds)
specifier|public
name|void
name|performExport
parameter_list|(
specifier|final
name|BibtexDatabase
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
name|String
name|encoding
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|entryIds
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
name|getSaveSession
argument_list|(
name|this
operator|.
name|encoding
argument_list|,
name|outFile
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
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|ss
operator|==
literal|null
condition|)
name|ss
operator|=
name|getSaveSession
argument_list|(
name|encoding
argument_list|,
name|outFile
argument_list|)
expr_stmt|;
name|VerifyingWriter
name|ps
init|=
name|ss
operator|.
name|getWriter
argument_list|()
decl_stmt|;
name|Layout
name|beginLayout
init|=
literal|null
decl_stmt|;
name|Reader
name|reader
init|=
literal|null
decl_stmt|;
comment|// Check if this export filter has bundled name formatters:
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|customNameFormatters
init|=
name|readFormatterFile
argument_list|(
name|lfFileName
argument_list|)
decl_stmt|;
comment|// Set a global field, so all layouts have access to the custom name formatters:
name|Globals
operator|.
name|prefs
operator|.
name|customExportNameFormatters
operator|=
name|customNameFormatters
expr_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|missingFormatters
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
comment|// Print header
try|try
block|{
name|reader
operator|=
name|getReader
argument_list|(
name|lfFileName
operator|+
literal|".begin.layout"
argument_list|)
expr_stmt|;
name|LayoutHelper
name|layoutHelper
init|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|beginLayout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
expr_stmt|;
name|reader
operator|.
name|close
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
comment|/* 		 * Write database entries; entries will be sorted as they appear on the 		 * screen, or sorted by author, depending on Preferences. We also supply 		 * the Set entries - if we are to export only certain entries, it will 		 * be non-null, and be used to choose entries. Otherwise, it will be 		 * null, and be ignored. 		 */
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|sorted
init|=
name|FileActions
operator|.
name|getSortedEntries
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|entryIds
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|// Load default layout
name|reader
operator|=
name|getReader
argument_list|(
name|lfFileName
operator|+
literal|".layout"
argument_list|)
expr_stmt|;
name|LayoutHelper
name|layoutHelper
init|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|Layout
name|defLayout
init|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
decl_stmt|;
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|defLayout
operator|.
name|getMissingFormatters
argument_list|()
argument_list|)
expr_stmt|;
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
argument_list|<
name|String
argument_list|,
name|Layout
argument_list|>
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
name|BibtexEntry
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
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
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
name|layout
operator|=
name|layouts
operator|.
name|get
argument_list|(
name|type
argument_list|)
expr_stmt|;
else|else
block|{
try|try
block|{
comment|// We try to get a type-specific layout for this entry.
name|reader
operator|=
name|getReader
argument_list|(
name|lfFileName
operator|+
literal|"."
operator|+
name|type
operator|+
literal|".layout"
argument_list|)
expr_stmt|;
name|layoutHelper
operator|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|)
expr_stmt|;
name|layout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
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
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
if|if
condition|(
name|layout
operator|!=
literal|null
condition|)
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
block|{
name|reader
operator|=
name|getReader
argument_list|(
name|lfFileName
operator|+
literal|".end.layout"
argument_list|)
expr_stmt|;
name|layoutHelper
operator|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|)
expr_stmt|;
name|endLayout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
expr_stmt|;
name|reader
operator|.
name|close
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
name|endLayout
operator|!=
literal|null
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
name|missingFormatters
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"The following formatters could not be found"
argument_list|)
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|missingFormatters
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|finalizeSaveSession
argument_list|(
name|ss
argument_list|)
expr_stmt|;
block|}
comment|/**      * See if there is a name formatter file bundled with this export format. If so, read      * all the name formatters so they can be used by the filter layouts.      * @param lfFileName The layout file name.      */
DECL|method|readFormatterFile (String lfFileName)
specifier|private
name|HashMap
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
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
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
name|Reader
name|in
init|=
literal|null
decl_stmt|;
try|try
block|{
name|in
operator|=
operator|new
name|FileReader
argument_list|(
name|formatterFile
argument_list|)
expr_stmt|;
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
name|length
argument_list|()
operator|==
literal|0
condition|)
continue|continue;
name|int
name|index
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|":"
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
name|index
operator|+
literal|1
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
comment|//System.out.println("Name: '"+formatterName+"'");
comment|//System.out.println("Contents: '"+contents+"'");
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
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|in
operator|!=
literal|null
condition|)
try|try
block|{
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
name|formatters
return|;
block|}
DECL|method|getSaveSession (final String encoding, final File outFile)
specifier|protected
name|SaveSession
name|getSaveSession
parameter_list|(
specifier|final
name|String
name|encoding
parameter_list|,
specifier|final
name|File
name|outFile
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|SaveSession
argument_list|(
name|outFile
argument_list|,
name|encoding
argument_list|,
literal|false
argument_list|)
return|;
block|}
comment|/** 	 * @see net.sf.jabref.export.IExportFormat#getFileFilter() 	 */
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
return|return
name|fileFilter
return|;
block|}
DECL|method|finalizeSaveSession (final SaveSession ss)
specifier|public
name|void
name|finalizeSaveSession
parameter_list|(
specifier|final
name|SaveSession
name|ss
parameter_list|)
throws|throws
name|Exception
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
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Could not encode..."
argument_list|)
expr_stmt|;
block|}
name|ss
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

