begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|texparser
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
name|LineNumberReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UncheckedIOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|channels
operator|.
name|ClosedChannelException
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
name|Files
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|regex
operator|.
name|Matcher
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
name|model
operator|.
name|texparser
operator|.
name|TexParser
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
name|texparser
operator|.
name|TexParserResult
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|tika
operator|.
name|parser
operator|.
name|txt
operator|.
name|CharsetDetector
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

begin_class
DECL|class|DefaultTexParser
specifier|public
class|class
name|DefaultTexParser
implements|implements
name|TexParser
block|{
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
name|DefaultTexParser
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|TEX_EXT
specifier|private
specifier|static
specifier|final
name|String
name|TEX_EXT
init|=
literal|".tex"
decl_stmt|;
DECL|field|BIB_EXT
specifier|private
specifier|static
specifier|final
name|String
name|BIB_EXT
init|=
literal|".bib"
decl_stmt|;
comment|/**      * It is allowed to add new cite commands for pattern matching. Some valid examples: "citep", "[cC]ite", and      * "[cC]ite(author|title|year|t|p)?".      */
DECL|field|CITE_COMMANDS
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|CITE_COMMANDS
init|=
block|{
literal|"[cC]ite(alt|alp|author|authorfull|date|num|p|t|text|title|url|year|yearpar)?"
block|,
literal|"([aA]|[aA]uto|fnote|foot|footfull|full|no|[nN]ote|[pP]aren|[pP]note|[tT]ext|[sS]mart|super)cite"
block|,
literal|"footcitetext"
block|,
literal|"(block|text)cquote"
block|}
decl_stmt|;
DECL|field|CITE_GROUP
specifier|private
specifier|static
specifier|final
name|String
name|CITE_GROUP
init|=
literal|"key"
decl_stmt|;
DECL|field|CITE_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|CITE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"\\\\(%s)\\*?(?:\\[(?:[^\\]]*)\\]){0,2}\\{(?<%s>[^\\}]*)\\}(?:\\{[^\\}]*\\})?"
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|"|"
argument_list|,
name|CITE_COMMANDS
argument_list|)
argument_list|,
name|CITE_GROUP
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|BIBLIOGRAPHY_GROUP
specifier|private
specifier|static
specifier|final
name|String
name|BIBLIOGRAPHY_GROUP
init|=
literal|"bib"
decl_stmt|;
DECL|field|BIBLIOGRAPHY_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|BIBLIOGRAPHY_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"\\\\(?:bibliography|addbibresource)\\{(?<%s>[^\\}]*)\\}"
argument_list|,
name|BIBLIOGRAPHY_GROUP
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|INCLUDE_GROUP
specifier|private
specifier|static
specifier|final
name|String
name|INCLUDE_GROUP
init|=
literal|"file"
decl_stmt|;
DECL|field|INCLUDE_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|INCLUDE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"\\\\(?:include|input)\\{(?<%s>[^\\}]*)\\}"
argument_list|,
name|INCLUDE_GROUP
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|texParserResult
specifier|private
specifier|final
name|TexParserResult
name|texParserResult
decl_stmt|;
DECL|method|DefaultTexParser ()
specifier|public
name|DefaultTexParser
parameter_list|()
block|{
name|this
operator|.
name|texParserResult
operator|=
operator|new
name|TexParserResult
argument_list|()
expr_stmt|;
block|}
DECL|method|getTexParserResult ()
specifier|public
name|TexParserResult
name|getTexParserResult
parameter_list|()
block|{
return|return
name|texParserResult
return|;
block|}
annotation|@
name|Override
DECL|method|parse (String citeString)
specifier|public
name|TexParserResult
name|parse
parameter_list|(
name|String
name|citeString
parameter_list|)
block|{
name|matchCitation
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|""
argument_list|)
argument_list|,
literal|1
argument_list|,
name|citeString
argument_list|)
expr_stmt|;
return|return
name|texParserResult
return|;
block|}
annotation|@
name|Override
DECL|method|parse (Path texFile)
specifier|public
name|TexParserResult
name|parse
parameter_list|(
name|Path
name|texFile
parameter_list|)
block|{
return|return
name|parse
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|texFile
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|parse (List<Path> texFiles)
specifier|public
name|TexParserResult
name|parse
parameter_list|(
name|List
argument_list|<
name|Path
argument_list|>
name|texFiles
parameter_list|)
block|{
name|texParserResult
operator|.
name|addFiles
argument_list|(
name|texFiles
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|referencedFiles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Path
name|file
range|:
name|texFiles
control|)
block|{
if|if
condition|(
operator|!
name|file
operator|.
name|toFile
argument_list|()
operator|.
name|exists
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"File does not exist: %s"
argument_list|,
name|file
argument_list|)
argument_list|)
expr_stmt|;
continue|continue;
block|}
try|try
init|(
name|LineNumberReader
name|lineNumberReader
init|=
operator|new
name|LineNumberReader
argument_list|(
operator|new
name|CharsetDetector
argument_list|()
operator|.
name|setText
argument_list|(
name|Files
operator|.
name|readAllBytes
argument_list|(
name|file
argument_list|)
argument_list|)
operator|.
name|detect
argument_list|()
operator|.
name|getReader
argument_list|()
argument_list|)
init|)
block|{
for|for
control|(
name|String
name|line
init|=
name|lineNumberReader
operator|.
name|readLine
argument_list|()
init|;
name|line
operator|!=
literal|null
condition|;
name|line
operator|=
name|lineNumberReader
operator|.
name|readLine
argument_list|()
control|)
block|{
comment|// Skip comments and blank lines.
if|if
condition|(
name|line
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|line
operator|.
name|trim
argument_list|()
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'%'
condition|)
block|{
continue|continue;
block|}
name|matchCitation
argument_list|(
name|file
argument_list|,
name|lineNumberReader
operator|.
name|getLineNumber
argument_list|()
argument_list|,
name|line
argument_list|)
expr_stmt|;
name|matchBibFile
argument_list|(
name|file
argument_list|,
name|line
argument_list|)
expr_stmt|;
name|matchNestedFile
argument_list|(
name|file
argument_list|,
name|texFiles
argument_list|,
name|referencedFiles
argument_list|,
name|line
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|ClosedChannelException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Parsing has been interrupted"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|UncheckedIOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while parsing file {}"
argument_list|,
name|file
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Parse all files referenced by TEX files, recursively.
if|if
condition|(
operator|!
name|referencedFiles
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|parse
argument_list|(
name|referencedFiles
argument_list|)
expr_stmt|;
block|}
return|return
name|texParserResult
return|;
block|}
comment|/**      * Find cites along a specific line and store them.      */
DECL|method|matchCitation (Path file, int lineNumber, String line)
specifier|private
name|void
name|matchCitation
parameter_list|(
name|Path
name|file
parameter_list|,
name|int
name|lineNumber
parameter_list|,
name|String
name|line
parameter_list|)
block|{
name|Matcher
name|citeMatch
init|=
name|CITE_PATTERN
operator|.
name|matcher
argument_list|(
name|line
argument_list|)
decl_stmt|;
while|while
condition|(
name|citeMatch
operator|.
name|find
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|key
range|:
name|citeMatch
operator|.
name|group
argument_list|(
name|CITE_GROUP
argument_list|)
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|texParserResult
operator|.
name|addKey
argument_list|(
name|key
operator|.
name|trim
argument_list|()
argument_list|,
name|file
argument_list|,
name|lineNumber
argument_list|,
name|citeMatch
operator|.
name|start
argument_list|()
argument_list|,
name|citeMatch
operator|.
name|end
argument_list|()
argument_list|,
name|line
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Find BIB files along a specific line and store them.      */
DECL|method|matchBibFile (Path file, String line)
specifier|private
name|void
name|matchBibFile
parameter_list|(
name|Path
name|file
parameter_list|,
name|String
name|line
parameter_list|)
block|{
name|Matcher
name|bibliographyMatch
init|=
name|BIBLIOGRAPHY_PATTERN
operator|.
name|matcher
argument_list|(
name|line
argument_list|)
decl_stmt|;
while|while
condition|(
name|bibliographyMatch
operator|.
name|find
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|bibString
range|:
name|bibliographyMatch
operator|.
name|group
argument_list|(
name|BIBLIOGRAPHY_GROUP
argument_list|)
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|bibString
operator|=
name|bibString
operator|.
name|trim
argument_list|()
expr_stmt|;
name|Path
name|bibFile
init|=
name|file
operator|.
name|getParent
argument_list|()
operator|.
name|resolve
argument_list|(
name|bibString
operator|.
name|endsWith
argument_list|(
name|BIB_EXT
argument_list|)
condition|?
name|bibString
else|:
name|String
operator|.
name|format
argument_list|(
literal|"%s%s"
argument_list|,
name|bibString
argument_list|,
name|BIB_EXT
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|bibFile
operator|.
name|toFile
argument_list|()
operator|.
name|exists
argument_list|()
condition|)
block|{
name|texParserResult
operator|.
name|addBibFile
argument_list|(
name|file
argument_list|,
name|bibFile
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**      * Find inputs and includes along a specific line and store them for parsing later.      */
DECL|method|matchNestedFile (Path file, List<Path> texFiles, List<Path> referencedFiles, String line)
specifier|private
name|void
name|matchNestedFile
parameter_list|(
name|Path
name|file
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|texFiles
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|referencedFiles
parameter_list|,
name|String
name|line
parameter_list|)
block|{
name|Matcher
name|includeMatch
init|=
name|INCLUDE_PATTERN
operator|.
name|matcher
argument_list|(
name|line
argument_list|)
decl_stmt|;
while|while
condition|(
name|includeMatch
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|include
init|=
name|includeMatch
operator|.
name|group
argument_list|(
name|INCLUDE_GROUP
argument_list|)
decl_stmt|;
name|Path
name|nestedFile
init|=
name|file
operator|.
name|getParent
argument_list|()
operator|.
name|resolve
argument_list|(
name|include
operator|.
name|endsWith
argument_list|(
name|TEX_EXT
argument_list|)
condition|?
name|include
else|:
name|String
operator|.
name|format
argument_list|(
literal|"%s%s"
argument_list|,
name|include
argument_list|,
name|TEX_EXT
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|nestedFile
operator|.
name|toFile
argument_list|()
operator|.
name|exists
argument_list|()
operator|&&
operator|!
name|texFiles
operator|.
name|contains
argument_list|(
name|nestedFile
argument_list|)
condition|)
block|{
name|referencedFiles
operator|.
name|add
argument_list|(
name|nestedFile
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

