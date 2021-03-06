begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|HashMap
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
name|Map
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
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatterPreferences
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|FileType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|StandardFileType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
import|;
end_import

begin_import
import|import
name|org
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|types
operator|.
name|EntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Base class for export formats based on templates.  */
end_comment

begin_class
DECL|class|TemplateExporter
specifier|public
class|class
name|TemplateExporter
extends|extends
name|Exporter
block|{
DECL|field|LAYOUT_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|LAYOUT_PREFIX
init|=
literal|"/resource/layout/"
decl_stmt|;
comment|/**      * A regular expression that matches blank lines      *      * ?m activates "multimode", which makes ^ match line starts/ends.      * \\s simply marks any whitespace character      */
DECL|field|BLANK_LINE_MATCHER
specifier|private
specifier|static
specifier|final
name|Pattern
name|BLANK_LINE_MATCHER
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?m)^\\s"
argument_list|)
decl_stmt|;
DECL|field|LAYOUT_EXTENSION
specifier|private
specifier|static
specifier|final
name|String
name|LAYOUT_EXTENSION
init|=
literal|".layout"
decl_stmt|;
DECL|field|FORMATTERS_EXTENSION
specifier|private
specifier|static
specifier|final
name|String
name|FORMATTERS_EXTENSION
init|=
literal|".formatters"
decl_stmt|;
DECL|field|BEGIN_INFIX
specifier|private
specifier|static
specifier|final
name|String
name|BEGIN_INFIX
init|=
literal|".begin"
decl_stmt|;
DECL|field|END_INFIX
specifier|private
specifier|static
specifier|final
name|String
name|END_INFIX
init|=
literal|".end"
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|TemplateExporter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|lfFileName
specifier|private
specifier|final
name|String
name|lfFileName
decl_stmt|;
DECL|field|directory
specifier|private
specifier|final
name|String
name|directory
decl_stmt|;
DECL|field|layoutPreferences
specifier|private
specifier|final
name|LayoutFormatterPreferences
name|layoutPreferences
decl_stmt|;
DECL|field|savePreferences
specifier|private
specifier|final
name|SavePreferences
name|savePreferences
decl_stmt|;
DECL|field|encoding
specifier|private
name|Charset
name|encoding
decl_stmt|;
comment|// If this value is set, it will be used to override the default encoding for the getCurrentBasePanel.
DECL|field|customExport
specifier|private
name|boolean
name|customExport
decl_stmt|;
DECL|field|deleteBlankLines
specifier|private
name|boolean
name|deleteBlankLines
decl_stmt|;
comment|/**      * Initialize another export format based on templates stored in dir with      * layoutFile lfFilename.      *      * @param displayName Name to display to the user.      * @param consoleName Name to call this format in the console.      * @param lfFileName  Name of the main layout file.      * @param directory   Directory in which to find the layout file.      * @param extension   Should contain the . (for instance .txt).      */
DECL|method|TemplateExporter (String displayName, String consoleName, String lfFileName, String directory, FileType extension)
specifier|public
name|TemplateExporter
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
name|FileType
name|extension
parameter_list|)
block|{
name|this
argument_list|(
name|displayName
argument_list|,
name|consoleName
argument_list|,
name|lfFileName
argument_list|,
name|directory
argument_list|,
name|extension
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initialize another export format based on templates stored in dir with      * layoutFile lfFilename.      *      * @param name              to display to the user and to call this format in the console.      * @param lfFileName        Name of the main layout file.      * @param extension         May or may not contain the . (for instance .txt).      * @param layoutPreferences Preferences for the layout      * @param savePreferences   Preferences for saving      */
DECL|method|TemplateExporter (String name, String lfFileName, String extension, LayoutFormatterPreferences layoutPreferences, SavePreferences savePreferences)
specifier|public
name|TemplateExporter
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|lfFileName
parameter_list|,
name|String
name|extension
parameter_list|,
name|LayoutFormatterPreferences
name|layoutPreferences
parameter_list|,
name|SavePreferences
name|savePreferences
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|name
argument_list|,
name|lfFileName
argument_list|,
literal|null
argument_list|,
name|StandardFileType
operator|.
name|newFileType
argument_list|(
name|extension
argument_list|)
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initialize another export format based on templates stored in dir with      * layoutFile lfFilename.      *      * @param displayName Name to display to the user.      * @param consoleName Name to call this format in the console.      * @param lfFileName  Name of the main layout file.      * @param directory   Directory in which to find the layout file.      * @param extension   Should contain the . (for instance .txt).      * @param layoutPreferences Preferences for layout      * @param savePreferences Preferences for saving      */
DECL|method|TemplateExporter (String displayName, String consoleName, String lfFileName, String directory, FileType extension, LayoutFormatterPreferences layoutPreferences, SavePreferences savePreferences)
specifier|public
name|TemplateExporter
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
name|FileType
name|extension
parameter_list|,
name|LayoutFormatterPreferences
name|layoutPreferences
parameter_list|,
name|SavePreferences
name|savePreferences
parameter_list|)
block|{
name|super
argument_list|(
name|consoleName
argument_list|,
name|displayName
argument_list|,
name|extension
argument_list|)
expr_stmt|;
if|if
condition|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|lfFileName
argument_list|)
operator|.
name|endsWith
argument_list|(
name|LAYOUT_EXTENSION
argument_list|)
condition|)
block|{
name|this
operator|.
name|lfFileName
operator|=
name|lfFileName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|lfFileName
operator|.
name|length
argument_list|()
operator|-
name|LAYOUT_EXTENSION
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|lfFileName
operator|=
name|lfFileName
expr_stmt|;
block|}
name|this
operator|.
name|directory
operator|=
name|directory
expr_stmt|;
name|this
operator|.
name|layoutPreferences
operator|=
name|layoutPreferences
expr_stmt|;
name|this
operator|.
name|savePreferences
operator|=
name|savePreferences
expr_stmt|;
block|}
comment|/**      * Initialize another export format based on templates stored in dir with      * layoutFile lfFilename.      * The display name is automatically derived from the FileType      *      *      * @param consoleName Name to call this format in the console.      * @param lfFileName  Name of the main layout file.      * @param directory   Directory in which to find the layout file.      * @param extension   Should contain the . (for instance .txt).      * @param layoutPreferences Preferences for layout      * @param savePreferences Preferences for saving      * @param deleteBlankLines If blank lines should be remove (default: false)      */
DECL|method|TemplateExporter (String consoleName, String lfFileName, String directory, StandardFileType extension, LayoutFormatterPreferences layoutPreferences, SavePreferences savePreferences, boolean deleteBlankLines)
specifier|public
name|TemplateExporter
parameter_list|(
name|String
name|consoleName
parameter_list|,
name|String
name|lfFileName
parameter_list|,
name|String
name|directory
parameter_list|,
name|StandardFileType
name|extension
parameter_list|,
name|LayoutFormatterPreferences
name|layoutPreferences
parameter_list|,
name|SavePreferences
name|savePreferences
parameter_list|,
name|boolean
name|deleteBlankLines
parameter_list|)
block|{
name|this
argument_list|(
name|consoleName
argument_list|,
name|consoleName
argument_list|,
name|lfFileName
argument_list|,
name|directory
argument_list|,
name|extension
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|deleteBlankLines
operator|=
name|deleteBlankLines
expr_stmt|;
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
comment|/**      * Set an encoding which will be used in preference to the default value      * obtained from the basepanel.      *      * @param encoding The name of the encoding to use.      */
DECL|method|withEncoding (Charset encoding)
specifier|public
name|TemplateExporter
name|withEncoding
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
return|return
name|this
return|;
block|}
comment|/**      * This method should return a reader from which the given layout file can      * be read.      *<p>      * Subclasses of TemplateExporter are free to override and provide their own      * implementation.      *      * @param filename the filename      * @return a newly created reader      * @throws IOException if the reader could not be created      */
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
comment|// loading it as a resource (from within JAR), or as a normal file. If
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
comment|// Try loading as a resource first. This works for files inside the JAR:
name|URL
name|reso
init|=
name|TemplateExporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|// If that did not work, try loading as a normal file URL:
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
annotation|@
name|Override
DECL|method|export (final BibDatabaseContext databaseContext, final Path file, final Charset encoding, List<BibEntry> entries)
specifier|public
name|void
name|export
parameter_list|(
specifier|final
name|BibDatabaseContext
name|databaseContext
parameter_list|,
specifier|final
name|Path
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
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entries
argument_list|)
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Do not export if no entries to export -- avoids exports with only template text
return|return;
block|}
try|try
init|(
name|AtomicFileWriter
name|ps
init|=
operator|new
name|AtomicFileWriter
argument_list|(
name|file
argument_list|,
name|encoding
argument_list|)
init|)
block|{
name|Layout
name|beginLayout
init|=
literal|null
decl_stmt|;
comment|// Check if this export filter has bundled name formatters:
comment|// Add these to the preferences, so all layouts have access to the custom name formatters:
name|readFormatterFile
argument_list|()
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
name|BEGIN_INFIX
operator|+
name|LAYOUT_EXTENSION
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
name|layoutPreferences
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
name|databaseContext
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
name|databaseContext
argument_list|,
name|entries
argument_list|,
name|savePreferences
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
name|LAYOUT_EXTENSION
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
name|layoutPreferences
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
literal|"Missing formatters found "
argument_list|,
name|missingFormatters
argument_list|)
expr_stmt|;
block|}
block|}
name|Map
argument_list|<
name|EntryType
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
name|ExporterFactory
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
name|ExporterFactory
operator|.
name|entryNumber
operator|++
expr_stmt|;
comment|// Increment entry counter.
comment|// Get the layout
name|EntryType
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
operator|.
name|getName
argument_list|()
operator|+
name|LAYOUT_EXTENSION
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
name|layoutPreferences
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
if|if
condition|(
name|layout
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|deleteBlankLines
condition|)
block|{
name|String
name|withoutBlankLines
init|=
name|BLANK_LINE_MATCHER
operator|.
name|matcher
argument_list|(
name|layout
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|)
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|ps
operator|.
name|write
argument_list|(
name|withoutBlankLines
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
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
name|END_INFIX
operator|+
name|LAYOUT_EXTENSION
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
name|layoutPreferences
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
name|databaseContext
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
name|layoutPreferences
operator|.
name|clearCustomExportNameFormatters
argument_list|()
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
literal|"Formatters not found"
argument_list|,
name|sb
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * See if there is a name formatter file bundled with this export format. If so, read      * all the name formatters so they can be used by the filter layouts.      *      */
DECL|method|readFormatterFile ()
specifier|private
name|void
name|readFormatterFile
parameter_list|()
block|{
name|File
name|formatterFile
init|=
operator|new
name|File
argument_list|(
name|lfFileName
operator|+
name|FORMATTERS_EXTENSION
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
name|layoutPreferences
operator|.
name|putCustomExportNameFormatter
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
block|}
DECL|method|getLayoutFileName ()
specifier|public
name|String
name|getLayoutFileName
parameter_list|()
block|{
return|return
name|lfFileName
return|;
block|}
DECL|method|getLayoutFileNameWithExtension ()
specifier|public
name|String
name|getLayoutFileNameWithExtension
parameter_list|()
block|{
return|return
name|lfFileName
operator|+
name|LAYOUT_EXTENSION
return|;
block|}
block|}
end_class

end_unit

