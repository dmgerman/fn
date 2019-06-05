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
name|Citation
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
comment|/**      * It is allowed to add new cite commands for pattern matching.      *      *<p>Some valid examples: "citep", "[cC]ite", "[cC]ite(author|title|year|t|p)?"      *      *<p>TODO: Add support for multicite commands.      */
DECL|field|CITE_COMMANDS
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|CITE_COMMANDS
init|=
operator|new
name|String
index|[]
block|{
literal|"[cC]ite(alt|alp|author|authorfull|date|num|p|t|text|title|url|year|yearpar)?"
block|,
literal|"([aA]|fnote|foot|footfull|full|no|[nN]ote|[pP]aren|[pP]note|[tT]ext|[sS]mart|super)cite"
block|,
literal|"footcitetext"
block|}
decl_stmt|;
DECL|field|CITE_REGEX
specifier|private
specifier|static
specifier|final
name|String
name|CITE_REGEX
init|=
name|String
operator|.
name|format
argument_list|(
literal|"\\\\(?:%s)\\*?(?:\\[(?:[^\\]]*)\\]){0,2}\\{(?<key>[^\\}]*)\\}"
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|"|"
argument_list|,
name|CITE_COMMANDS
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|INCLUDE_REGEX
specifier|private
specifier|static
specifier|final
name|String
name|INCLUDE_REGEX
init|=
literal|"\\\\(?:include|input)\\{(?<file>[^\\}]*)\\}"
decl_stmt|;
DECL|field|result
specifier|private
specifier|final
name|TexParserResult
name|result
decl_stmt|;
DECL|method|DefaultTexParser ()
specifier|public
name|DefaultTexParser
parameter_list|()
block|{
name|this
operator|.
name|result
operator|=
operator|new
name|TexParserResult
argument_list|()
expr_stmt|;
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
literal|"foo/bar"
argument_list|)
argument_list|,
literal|1
argument_list|,
name|citeString
argument_list|)
expr_stmt|;
return|return
name|result
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
if|if
condition|(
name|result
operator|.
name|getFileList
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|result
operator|.
name|getFileList
argument_list|()
operator|.
name|addAll
argument_list|(
name|texFiles
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|getNestedFiles
argument_list|()
operator|.
name|addAll
argument_list|(
name|texFiles
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|fileIndex
init|=
literal|0
init|;
name|fileIndex
operator|<
name|texFiles
operator|.
name|size
argument_list|()
condition|;
name|fileIndex
operator|++
control|)
block|{
name|Path
name|file
init|=
name|texFiles
operator|.
name|get
argument_list|(
name|fileIndex
argument_list|)
decl_stmt|;
try|try
init|(
name|LineNumberReader
name|lnr
init|=
operator|new
name|LineNumberReader
argument_list|(
name|Files
operator|.
name|newBufferedReader
argument_list|(
name|file
argument_list|)
argument_list|)
init|)
block|{
for|for
control|(
name|String
name|line
init|=
name|lnr
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
name|lnr
operator|.
name|readLine
argument_list|()
control|)
block|{
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
literal|"%"
argument_list|)
condition|)
block|{
comment|// Skip comment lines.
continue|continue;
block|}
name|matchCitation
argument_list|(
name|file
argument_list|,
name|lnr
operator|.
name|getLineNumber
argument_list|()
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
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Error opening the TEX file"
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
name|result
return|;
block|}
comment|/**      * Find cites along a specific line and add them to a map.      */
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
name|Pattern
operator|.
name|compile
argument_list|(
name|CITE_REGEX
argument_list|)
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
name|String
index|[]
name|keys
init|=
name|citeMatch
operator|.
name|group
argument_list|(
literal|"key"
argument_list|)
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|keys
control|)
block|{
name|Citation
name|citation
init|=
operator|new
name|Citation
argument_list|(
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
decl_stmt|;
if|if
condition|(
operator|!
name|result
operator|.
name|getCitations
argument_list|()
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|result
operator|.
name|getCitations
argument_list|()
operator|.
name|put
argument_list|(
name|key
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|result
operator|.
name|getCitations
argument_list|()
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|contains
argument_list|(
name|citation
argument_list|)
condition|)
block|{
name|result
operator|.
name|getCitations
argument_list|()
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|add
argument_list|(
name|citation
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
name|Pattern
operator|.
name|compile
argument_list|(
name|INCLUDE_REGEX
argument_list|)
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
literal|"file"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|include
operator|.
name|endsWith
argument_list|(
literal|".tex"
argument_list|)
condition|)
block|{
name|include
operator|+=
literal|".tex"
expr_stmt|;
block|}
name|Path
name|folder
init|=
name|file
operator|.
name|getParent
argument_list|()
decl_stmt|;
name|Path
name|inputFile
init|=
operator|(
name|folder
operator|!=
literal|null
operator|)
condition|?
name|folder
operator|.
name|resolve
argument_list|(
name|include
argument_list|)
else|:
name|Paths
operator|.
name|get
argument_list|(
name|include
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|texFiles
operator|.
name|contains
argument_list|(
name|inputFile
argument_list|)
condition|)
block|{
name|referencedFiles
operator|.
name|add
argument_list|(
name|inputFile
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

