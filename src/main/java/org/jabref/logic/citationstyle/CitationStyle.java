begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.citationstyle
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|citationstyle
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
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
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
name|StandardCharsets
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
name|FileSystem
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
name|FileSystems
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
name|NoSuchFileException
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
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
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
name|de
operator|.
name|undercouch
operator|.
name|citeproc
operator|.
name|helper
operator|.
name|CSLUtils
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

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|CharacterData
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
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
name|InputSource
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

begin_comment
comment|/**  * Representation of a CitationStyle. Stores its name, the file path and the style itself  */
end_comment

begin_class
DECL|class|CitationStyle
specifier|public
class|class
name|CitationStyle
block|{
DECL|field|DEFAULT
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT
init|=
literal|"/ieee.csl"
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
name|CitationStyle
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|STYLES_ROOT
specifier|private
specifier|static
specifier|final
name|String
name|STYLES_ROOT
init|=
literal|"/csl-styles"
decl_stmt|;
DECL|field|STYLES
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|CitationStyle
argument_list|>
name|STYLES
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|filePath
specifier|private
specifier|final
name|String
name|filePath
decl_stmt|;
DECL|field|title
specifier|private
specifier|final
name|String
name|title
decl_stmt|;
DECL|field|source
specifier|private
specifier|final
name|String
name|source
decl_stmt|;
DECL|method|CitationStyle (final String filename, final String title, final String source)
specifier|private
name|CitationStyle
parameter_list|(
specifier|final
name|String
name|filename
parameter_list|,
specifier|final
name|String
name|title
parameter_list|,
specifier|final
name|String
name|source
parameter_list|)
block|{
name|this
operator|.
name|filePath
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|filename
argument_list|)
expr_stmt|;
name|this
operator|.
name|title
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|title
argument_list|)
expr_stmt|;
name|this
operator|.
name|source
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|source
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates an CitationStyle instance out of the style string      */
DECL|method|createCitationStyleFromSource (final String source, final String filename)
specifier|private
specifier|static
name|Optional
argument_list|<
name|CitationStyle
argument_list|>
name|createCitationStyleFromSource
parameter_list|(
specifier|final
name|String
name|source
parameter_list|,
specifier|final
name|String
name|filename
parameter_list|)
block|{
if|if
condition|(
operator|(
name|filename
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|filename
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|(
name|source
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|source
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|DocumentBuilder
name|db
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|InputSource
name|is
init|=
operator|new
name|InputSource
argument_list|()
decl_stmt|;
name|is
operator|.
name|setCharacterStream
argument_list|(
operator|new
name|StringReader
argument_list|(
name|stripInvalidProlog
argument_list|(
name|source
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Document
name|doc
init|=
name|db
operator|.
name|parse
argument_list|(
name|is
argument_list|)
decl_stmt|;
name|NodeList
name|nodes
init|=
name|doc
operator|.
name|getElementsByTagName
argument_list|(
literal|"info"
argument_list|)
decl_stmt|;
name|NodeList
name|titleNode
init|=
operator|(
operator|(
name|Element
operator|)
name|nodes
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|getElementsByTagName
argument_list|(
literal|"title"
argument_list|)
decl_stmt|;
name|String
name|title
init|=
operator|(
operator|(
name|CharacterData
operator|)
name|titleNode
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|.
name|getFirstChild
argument_list|()
operator|)
operator|.
name|getData
argument_list|()
decl_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|CitationStyle
argument_list|(
name|filename
argument_list|,
name|title
argument_list|,
name|source
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
decl||
name|SAXException
decl||
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while parsing source"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|stripInvalidProlog (String source)
specifier|private
specifier|static
name|String
name|stripInvalidProlog
parameter_list|(
name|String
name|source
parameter_list|)
block|{
name|int
name|startIndex
init|=
name|source
operator|.
name|indexOf
argument_list|(
literal|"<"
argument_list|)
decl_stmt|;
if|if
condition|(
name|startIndex
operator|>
literal|0
condition|)
block|{
return|return
name|source
operator|.
name|substring
argument_list|(
name|startIndex
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|source
return|;
block|}
block|}
comment|/**      * Loads the CitationStyle from the given file      */
DECL|method|createCitationStyleFromFile (final String styleFile)
specifier|public
specifier|static
name|Optional
argument_list|<
name|CitationStyle
argument_list|>
name|createCitationStyleFromFile
parameter_list|(
specifier|final
name|String
name|styleFile
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isCitationStyleFile
argument_list|(
name|styleFile
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Can only load style files: {}"
argument_list|,
name|styleFile
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
try|try
block|{
name|String
name|text
decl_stmt|;
name|String
name|internalFile
init|=
name|STYLES_ROOT
operator|+
operator|(
name|styleFile
operator|.
name|startsWith
argument_list|(
literal|"/"
argument_list|)
condition|?
literal|""
else|:
literal|"/"
operator|)
operator|+
name|styleFile
decl_stmt|;
name|URL
name|url
init|=
name|CitationStyle
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|internalFile
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|!=
literal|null
condition|)
block|{
name|text
operator|=
name|CSLUtils
operator|.
name|readURLToString
argument_list|(
name|url
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// if the url is null then the style is located outside the classpath
name|text
operator|=
operator|new
name|String
argument_list|(
name|Files
operator|.
name|readAllBytes
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|styleFile
argument_list|)
argument_list|)
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
expr_stmt|;
block|}
return|return
name|createCitationStyleFromSource
argument_list|(
name|text
argument_list|,
name|styleFile
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchFileException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not find file: "
operator|+
name|styleFile
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error reading source file"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|/**      * Provides the default citation style which is currently IEEE      *      * @return default citation style      */
DECL|method|getDefault ()
specifier|public
specifier|static
name|CitationStyle
name|getDefault
parameter_list|()
block|{
return|return
name|createCitationStyleFromFile
argument_list|(
name|DEFAULT
argument_list|)
operator|.
name|orElse
argument_list|(
operator|new
name|CitationStyle
argument_list|(
literal|""
argument_list|,
literal|"Empty"
argument_list|,
literal|""
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Provides the citation styles that come with JabRef.      *      * @return list of available citation styles      */
DECL|method|discoverCitationStyles ()
specifier|public
specifier|static
name|List
argument_list|<
name|CitationStyle
argument_list|>
name|discoverCitationStyles
parameter_list|()
block|{
if|if
condition|(
operator|!
name|STYLES
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|STYLES
return|;
block|}
name|URL
name|url
init|=
name|CitationStyle
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|STYLES_ROOT
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
try|try
block|{
name|URI
name|uri
init|=
name|url
operator|.
name|toURI
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"jar"
operator|.
name|equals
argument_list|(
name|uri
operator|.
name|getScheme
argument_list|()
argument_list|)
condition|)
block|{
try|try
init|(
name|FileSystem
name|fs
init|=
name|FileSystems
operator|.
name|newFileSystem
argument_list|(
name|uri
argument_list|,
name|Collections
operator|.
name|emptyMap
argument_list|()
argument_list|)
init|)
block|{
name|Path
name|path
init|=
name|fs
operator|.
name|getPath
argument_list|(
name|STYLES_ROOT
argument_list|)
decl_stmt|;
name|STYLES
operator|.
name|addAll
argument_list|(
name|discoverCitationStylesInPath
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|STYLES
operator|.
name|addAll
argument_list|(
name|discoverCitationStylesInPath
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|uri
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|STYLES
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
decl||
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"something went wrong while searching available CitationStyles. Are you running directly from source code?"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
DECL|method|discoverCitationStylesInPath (Path path)
specifier|private
specifier|static
name|List
argument_list|<
name|CitationStyle
argument_list|>
name|discoverCitationStylesInPath
parameter_list|(
name|Path
name|path
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|stream
init|=
name|Files
operator|.
name|find
argument_list|(
name|path
argument_list|,
literal|1
argument_list|,
parameter_list|(
name|file
parameter_list|,
name|attr
parameter_list|)
lambda|->
name|file
operator|.
name|toString
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"csl"
argument_list|)
argument_list|)
init|)
block|{
return|return
name|stream
operator|.
name|map
argument_list|(
name|Path
operator|::
name|getFileName
argument_list|)
operator|.
name|map
argument_list|(
name|Path
operator|::
name|toString
argument_list|)
operator|.
name|map
argument_list|(
name|CitationStyle
operator|::
name|createCitationStyleFromFile
argument_list|)
operator|.
name|filter
argument_list|(
name|Optional
operator|::
name|isPresent
argument_list|)
operator|.
name|map
argument_list|(
name|Optional
operator|::
name|get
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**      * Checks if the given style file is a CitationStyle      */
DECL|method|isCitationStyleFile (String styleFile)
specifier|public
specifier|static
name|boolean
name|isCitationStyleFile
parameter_list|(
name|String
name|styleFile
parameter_list|)
block|{
return|return
name|StandardFileType
operator|.
name|CITATION_STYLE
operator|.
name|getExtensions
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|styleFile
operator|::
name|endsWith
argument_list|)
return|;
block|}
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|title
return|;
block|}
DECL|method|getSource ()
specifier|public
name|String
name|getSource
parameter_list|()
block|{
return|return
name|source
return|;
block|}
DECL|method|getFilePath ()
specifier|public
name|String
name|getFilePath
parameter_list|()
block|{
return|return
name|filePath
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|title
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|CitationStyle
name|other
init|=
operator|(
name|CitationStyle
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|source
argument_list|,
name|other
operator|.
name|source
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|source
argument_list|)
return|;
block|}
block|}
end_class

end_unit

