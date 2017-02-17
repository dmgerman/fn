begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
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
name|IOException
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
name|Optional
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
name|AbstractParamLayoutFormatter
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
name|io
operator|.
name|FileUtil
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
name|FileField
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
name|ParsedFileField
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

begin_comment
comment|/**  * This formatter iterates over all file links, or all file links of a specified  * type, outputting a format string given as the first argument. The format string  * can contain a number of escape sequences indicating file link information to  * be inserted into the string.  *<p/>  * This formatter can take an optional second argument specifying the name of a file  * type. If specified, the iteration will only include those files with a file type  * matching the given name (case-insensitively). If specified as an empty argument,  * all file links will be included.  *<p/>  * After the second argument, pairs of additional arguments can be added in order to  * specify regular expression replacements to be done upon the inserted link information  * before insertion into the output string. A non-paired argument will be ignored.  * In order to specify replacements without filtering on file types, use an empty second  * argument.  *<p/>  *<p/>  *<p/>  * The escape sequences for embedding information are as follows:  *<p/>  * \i   : This inserts the iteration index (starting from 1), and can be useful if  * the output list of files should be enumerated.  * \p   : This inserts the file path of the file link. Relative links below your file directory  *        will be expanded to their absolute path.  * \r   : This inserts the file path without expanding relative links.  * \f   : This inserts the name of the file link's type.  * \x   : This inserts the file's extension, if any.  * \d   : This inserts the file link's description, if any.  *<p/>  * For instance, an entry could contain a file link to the file "/home/john/report.pdf"  * of the "PDF" type with description "John's final report".  *<p/>  * Using the WrapFileLinks formatter with the following argument:  *<p/>  * \format[WrapFileLinks(\i. \d (\p))]{\file}  *<p/>  * would give the following output:  * 1. John's final report (/home/john/report.pdf)  *<p/>  * If the entry contained a second file link to the file "/home/john/draft.txt" of the  * "Text file" type with description 'An early "draft"', the output would be as follows:  * 1. John's final report (/home/john/report.pdf)  * 2. An early "draft" (/home/john/draft.txt)  *<p/>  * If the formatter was called with a second argument, the list would be filtered.  * For instance:  * \format[WrapFileLinks(\i. \d (\p),text file)]{\file}  *<p/>  * would show only the text file:  * 1. An early "draft" (/home/john/draft.txt)  *<p/>  * If we wanted this output to be part of an XML styled output, the quotes in the  * file description could cause problems. Adding two additional arguments to translate  * the quotes into XML characters solves this:  * \format[WrapFileLinks(\i. \d (\p),text file,",&quot;)]{\file}  *<p/>  * would give the following output:  * 1. An early&quot;draft&quot; (/home/john/draft.txt)  *  * Additional pairs of replacements can be added.  */
end_comment

begin_class
DECL|class|WrapFileLinks
specifier|public
class|class
name|WrapFileLinks
extends|extends
name|AbstractParamLayoutFormatter
block|{
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
name|WrapFileLinks
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|fileType
specifier|private
name|String
name|fileType
decl_stmt|;
DECL|field|format
specifier|private
name|List
argument_list|<
name|FormatEntry
argument_list|>
name|format
decl_stmt|;
DECL|field|replacements
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|replacements
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Define codes for the various escape sequences that can be inserted:
DECL|field|STRING
specifier|private
specifier|static
specifier|final
name|int
name|STRING
init|=
literal|0
decl_stmt|;
DECL|field|ITERATION_COUNT
specifier|private
specifier|static
specifier|final
name|int
name|ITERATION_COUNT
init|=
literal|1
decl_stmt|;
DECL|field|FILE_PATH
specifier|private
specifier|static
specifier|final
name|int
name|FILE_PATH
init|=
literal|2
decl_stmt|;
DECL|field|FILE_TYPE
specifier|private
specifier|static
specifier|final
name|int
name|FILE_TYPE
init|=
literal|3
decl_stmt|;
DECL|field|FILE_EXTENSION
specifier|private
specifier|static
specifier|final
name|int
name|FILE_EXTENSION
init|=
literal|4
decl_stmt|;
DECL|field|FILE_DESCRIPTION
specifier|private
specifier|static
specifier|final
name|int
name|FILE_DESCRIPTION
init|=
literal|5
decl_stmt|;
DECL|field|RELATIVE_FILE_PATH
specifier|private
specifier|static
specifier|final
name|int
name|RELATIVE_FILE_PATH
init|=
literal|6
decl_stmt|;
comment|// Define which escape sequences give what results:
DECL|field|ESCAPE_SEQ
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|Character
argument_list|,
name|Integer
argument_list|>
name|ESCAPE_SEQ
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|FileLinkPreferences
name|prefs
decl_stmt|;
static|static
block|{
name|WrapFileLinks
operator|.
name|ESCAPE_SEQ
operator|.
name|put
argument_list|(
literal|'i'
argument_list|,
name|WrapFileLinks
operator|.
name|ITERATION_COUNT
argument_list|)
expr_stmt|;
name|WrapFileLinks
operator|.
name|ESCAPE_SEQ
operator|.
name|put
argument_list|(
literal|'p'
argument_list|,
name|WrapFileLinks
operator|.
name|FILE_PATH
argument_list|)
expr_stmt|;
name|WrapFileLinks
operator|.
name|ESCAPE_SEQ
operator|.
name|put
argument_list|(
literal|'r'
argument_list|,
name|WrapFileLinks
operator|.
name|RELATIVE_FILE_PATH
argument_list|)
expr_stmt|;
name|WrapFileLinks
operator|.
name|ESCAPE_SEQ
operator|.
name|put
argument_list|(
literal|'f'
argument_list|,
name|WrapFileLinks
operator|.
name|FILE_TYPE
argument_list|)
expr_stmt|;
name|WrapFileLinks
operator|.
name|ESCAPE_SEQ
operator|.
name|put
argument_list|(
literal|'x'
argument_list|,
name|WrapFileLinks
operator|.
name|FILE_EXTENSION
argument_list|)
expr_stmt|;
name|WrapFileLinks
operator|.
name|ESCAPE_SEQ
operator|.
name|put
argument_list|(
literal|'d'
argument_list|,
name|WrapFileLinks
operator|.
name|FILE_DESCRIPTION
argument_list|)
expr_stmt|;
block|}
DECL|method|WrapFileLinks (FileLinkPreferences fileLinkPreferences)
specifier|public
name|WrapFileLinks
parameter_list|(
name|FileLinkPreferences
name|fileLinkPreferences
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|fileLinkPreferences
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|parts
init|=
name|AbstractParamLayoutFormatter
operator|.
name|parseArgument
argument_list|(
name|arg
argument_list|)
decl_stmt|;
name|format
operator|=
name|parseFormatString
argument_list|(
name|parts
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|parts
operator|.
name|size
argument_list|()
operator|>
literal|1
operator|)
operator|&&
operator|!
name|parts
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|fileType
operator|=
name|parts
operator|.
name|get
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|parts
operator|.
name|size
argument_list|()
operator|>
literal|2
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|2
init|;
name|i
operator|<
operator|(
name|parts
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
condition|;
name|i
operator|+=
literal|2
control|)
block|{
name|replacements
operator|.
name|put
argument_list|(
name|parts
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|parts
operator|.
name|get
argument_list|(
name|i
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|format (String field)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|field
parameter_list|)
block|{
if|if
condition|(
name|field
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|// Build the list containing the links:
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|fileList
init|=
name|FileField
operator|.
name|parse
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|int
name|piv
init|=
literal|1
decl_stmt|;
comment|// counter for relevant iterations
for|for
control|(
name|ParsedFileField
name|flEntry
range|:
name|fileList
control|)
block|{
comment|// Use this entry if we don't discriminate on types, or if the type fits:
if|if
condition|(
operator|(
name|fileType
operator|==
literal|null
operator|)
operator|||
name|flEntry
operator|.
name|getFileType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|fileType
argument_list|)
condition|)
block|{
for|for
control|(
name|FormatEntry
name|entry
range|:
name|format
control|)
block|{
switch|switch
condition|(
name|entry
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|STRING
case|:
name|sb
operator|.
name|append
argument_list|(
name|entry
operator|.
name|getString
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|ITERATION_COUNT
case|:
name|sb
operator|.
name|append
argument_list|(
name|piv
argument_list|)
expr_stmt|;
break|break;
case|case
name|FILE_PATH
case|:
name|List
argument_list|<
name|String
argument_list|>
name|dirs
decl_stmt|;
comment|// We need to resolve the file directory from the database's metadata,
comment|// but that is not available from a formatter. Therefore, as an
comment|// ugly hack, the export routine has set a global variable before
comment|// starting the export, which contains the database's file directory:
if|if
condition|(
operator|(
name|prefs
operator|.
name|getFileDirForDatabase
argument_list|()
operator|==
literal|null
operator|)
operator|||
name|prefs
operator|.
name|getFileDirForDatabase
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|dirs
operator|=
name|prefs
operator|.
name|getGeneratedDirForDatabase
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|dirs
operator|=
name|prefs
operator|.
name|getFileDirForDatabase
argument_list|()
expr_stmt|;
block|}
name|Optional
argument_list|<
name|File
argument_list|>
name|f
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|,
name|dirs
argument_list|)
decl_stmt|;
comment|/*                          * Stumbled over this while investigating                          *                          * https://sourceforge.net/tracker/index.php?func=detail&aid=1469903&group_id=92314&atid=600306                          */
if|if
condition|(
name|f
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
block|{
name|sb
operator|.
name|append
argument_list|(
name|replaceStrings
argument_list|(
name|f
operator|.
name|get
argument_list|()
operator|.
name|getCanonicalPath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem getting path"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|replaceStrings
argument_list|(
name|f
operator|.
name|get
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|replaceStrings
argument_list|(
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|RELATIVE_FILE_PATH
case|:
comment|/*                          * Stumbled over this while investigating                          *                          * https://sourceforge.net/tracker/index.php?func=detail&aid=1469903&group_id=92314&atid=600306                          */
name|sb
operator|.
name|append
argument_list|(
name|replaceStrings
argument_list|(
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//f.toURI().toString();
break|break;
case|case
name|FILE_EXTENSION
case|:
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|extension
lambda|->
name|sb
operator|.
name|append
argument_list|(
name|replaceStrings
argument_list|(
name|extension
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|FILE_TYPE
case|:
name|sb
operator|.
name|append
argument_list|(
name|replaceStrings
argument_list|(
name|flEntry
operator|.
name|getFileType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|FILE_DESCRIPTION
case|:
name|sb
operator|.
name|append
argument_list|(
name|replaceStrings
argument_list|(
name|flEntry
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
name|piv
operator|++
expr_stmt|;
comment|// update counter
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|replaceStrings (String text)
specifier|private
name|String
name|replaceStrings
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|String
name|result
init|=
name|text
decl_stmt|;
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
name|stringStringEntry
range|:
name|replacements
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|to
init|=
name|stringStringEntry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|result
operator|=
name|result
operator|.
name|replaceAll
argument_list|(
name|stringStringEntry
operator|.
name|getKey
argument_list|()
argument_list|,
name|to
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/**      * Parse a format string and return a list of FormatEntry objects. The format      * string is basically marked up with "\i" marking that the iteration number should      * be inserted, and with "\p" marking that the file path of the current iteration      * should be inserted, plus additional markers.      *      * @param format The marked-up string.      * @return the resulting format entries.      */
DECL|method|parseFormatString (String format)
specifier|private
specifier|static
name|List
argument_list|<
name|FormatEntry
argument_list|>
name|parseFormatString
parameter_list|(
name|String
name|format
parameter_list|)
block|{
name|List
argument_list|<
name|FormatEntry
argument_list|>
name|l
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
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
name|format
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|format
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|escaped
condition|)
block|{
name|escaped
operator|=
literal|false
expr_stmt|;
comment|// we know we'll be out of escape mode after this
comment|// Check if this escape sequence is meaningful:
if|if
condition|(
name|c
operator|==
literal|'\\'
condition|)
block|{
comment|// Escaped backslash: means that we add a backslash:
name|sb
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|WrapFileLinks
operator|.
name|ESCAPE_SEQ
operator|.
name|containsKey
argument_list|(
name|c
argument_list|)
condition|)
block|{
comment|// Ok, we have the code. Add the previous string (if any) and
comment|// the entry indicated by the escape sequence:
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|l
operator|.
name|add
argument_list|(
operator|new
name|FormatEntry
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Clear the buffer:
name|sb
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
block|}
name|l
operator|.
name|add
argument_list|(
operator|new
name|FormatEntry
argument_list|(
name|WrapFileLinks
operator|.
name|ESCAPE_SEQ
operator|.
name|get
argument_list|(
name|c
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Unknown escape sequence.
name|sb
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Check if we are at the start of an escape sequence:
if|if
condition|(
name|c
operator|==
literal|'\\'
condition|)
block|{
name|escaped
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Finished scanning the string. If we collected text at the end, add an entry for it:
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|l
operator|.
name|add
argument_list|(
operator|new
name|FormatEntry
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|l
return|;
block|}
comment|/**      * This class defines the building blocks of a parsed format strings. Each FormatEntry      * represents either a literal string or a piece of information pertaining to the file      * link to be exported or to the iteration through a series of file links. For literal      * strings this class encapsulates the literal itself, while for other types of information,      * only a type code is provided, and the subclass needs to fill in the proper information      * based on the file link to be exported or the iteration status.      */
DECL|class|FormatEntry
specifier|static
class|class
name|FormatEntry
block|{
DECL|field|type
specifier|private
specifier|final
name|int
name|type
decl_stmt|;
DECL|field|string
specifier|private
name|String
name|string
decl_stmt|;
DECL|method|FormatEntry (int type)
specifier|public
name|FormatEntry
parameter_list|(
name|int
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
DECL|method|FormatEntry (String value)
specifier|public
name|FormatEntry
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|WrapFileLinks
operator|.
name|STRING
expr_stmt|;
name|this
operator|.
name|string
operator|=
name|value
expr_stmt|;
block|}
DECL|method|getType ()
specifier|public
name|int
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
DECL|method|getString ()
specifier|public
name|String
name|getString
parameter_list|()
block|{
return|return
name|string
return|;
block|}
block|}
block|}
end_class

end_unit
