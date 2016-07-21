begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.fulltext
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|fulltext
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
name|InputStream
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
name|DOI
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
name|FieldName
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|HttpResponse
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|Unirest
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|exceptions
operator|.
name|UnirestException
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
name|NamedNodeMap
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
name|Node
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
name|SAXException
import|;
end_import

begin_comment
comment|/**  * FullTextFinder implementation that attempts to find a PDF URL at arXiv.  *  * @see http://arxiv.org/help/api/index  */
end_comment

begin_class
DECL|class|ArXiv
specifier|public
class|class
name|ArXiv
implements|implements
name|FullTextFinder
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
name|ArXiv
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|API_URL
specifier|private
specifier|static
specifier|final
name|String
name|API_URL
init|=
literal|"http://export.arxiv.org/api/query"
decl_stmt|;
annotation|@
name|Override
DECL|method|findFullText (BibEntry entry)
specifier|public
name|Optional
argument_list|<
name|URL
argument_list|>
name|findFullText
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|IOException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|URL
argument_list|>
name|pdfLink
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|// 1. DOI
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|)
operator|.
name|flatMap
argument_list|(
name|DOI
operator|::
name|build
argument_list|)
decl_stmt|;
comment|// 2. Eprint
name|Optional
argument_list|<
name|String
argument_list|>
name|eprint
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"eprint"
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|String
name|doiString
init|=
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
decl_stmt|;
comment|// Available in catalog?
try|try
block|{
name|Document
name|doc
init|=
name|queryApi
argument_list|(
name|doiString
argument_list|)
decl_stmt|;
name|NodeList
name|nodes
init|=
name|doc
operator|.
name|getElementsByTagName
argument_list|(
literal|"arxiv:doi"
argument_list|)
decl_stmt|;
name|Node
name|doiTag
init|=
name|nodes
operator|.
name|item
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|doiTag
operator|!=
literal|null
operator|)
operator|&&
name|doiTag
operator|.
name|getTextContent
argument_list|()
operator|.
name|equals
argument_list|(
name|doiString
argument_list|)
condition|)
block|{
comment|// Lookup PDF link
name|NodeList
name|links
init|=
name|doc
operator|.
name|getElementsByTagName
argument_list|(
literal|"link"
argument_list|)
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
name|links
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Node
name|link
init|=
name|links
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|NamedNodeMap
name|attr
init|=
name|link
operator|.
name|getAttributes
argument_list|()
decl_stmt|;
name|String
name|rel
init|=
name|attr
operator|.
name|getNamedItem
argument_list|(
literal|"rel"
argument_list|)
operator|.
name|getNodeValue
argument_list|()
decl_stmt|;
name|String
name|href
init|=
name|attr
operator|.
name|getNamedItem
argument_list|(
literal|"href"
argument_list|)
operator|.
name|getNodeValue
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"related"
operator|.
name|equals
argument_list|(
name|rel
argument_list|)
operator|&&
literal|"pdf"
operator|.
name|equals
argument_list|(
name|attr
operator|.
name|getNamedItem
argument_list|(
literal|"title"
argument_list|)
operator|.
name|getNodeValue
argument_list|()
argument_list|)
condition|)
block|{
name|pdfLink
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
name|href
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ arXiv."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|UnirestException
decl||
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
name|warn
argument_list|(
literal|"arXiv DOI API request failed"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|(
name|eprint
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
operator|!
name|eprint
operator|.
name|get
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
comment|// only lookup on id field
name|Document
name|doc
init|=
name|queryApi
argument_list|(
literal|"id:"
operator|+
name|eprint
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
comment|// Lookup PDF link
name|NodeList
name|links
init|=
name|doc
operator|.
name|getElementsByTagName
argument_list|(
literal|"link"
argument_list|)
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
name|links
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Node
name|link
init|=
name|links
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|NamedNodeMap
name|attr
init|=
name|link
operator|.
name|getAttributes
argument_list|()
decl_stmt|;
name|String
name|rel
init|=
name|attr
operator|.
name|getNamedItem
argument_list|(
literal|"rel"
argument_list|)
operator|.
name|getNodeValue
argument_list|()
decl_stmt|;
name|String
name|href
init|=
name|attr
operator|.
name|getNamedItem
argument_list|(
literal|"href"
argument_list|)
operator|.
name|getNodeValue
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"related"
operator|.
name|equals
argument_list|(
name|rel
argument_list|)
operator|&&
literal|"pdf"
operator|.
name|equals
argument_list|(
name|attr
operator|.
name|getNamedItem
argument_list|(
literal|"title"
argument_list|)
operator|.
name|getNodeValue
argument_list|()
argument_list|)
condition|)
block|{
name|pdfLink
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
name|href
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ arXiv."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|UnirestException
decl||
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
name|warn
argument_list|(
literal|"arXiv eprint API request failed"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|pdfLink
return|;
block|}
DECL|method|queryApi (String query)
specifier|private
name|Document
name|queryApi
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|SAXException
throws|,
name|ParserConfigurationException
throws|,
name|IOException
throws|,
name|UnirestException
block|{
name|HttpResponse
argument_list|<
name|InputStream
argument_list|>
name|response
init|=
name|Unirest
operator|.
name|get
argument_list|(
name|API_URL
argument_list|)
operator|.
name|queryString
argument_list|(
literal|"search_query"
argument_list|,
name|query
argument_list|)
operator|.
name|queryString
argument_list|(
literal|"max_results"
argument_list|,
literal|1
argument_list|)
operator|.
name|asBinary
argument_list|()
decl_stmt|;
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|DocumentBuilder
name|builder
init|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
return|return
name|builder
operator|.
name|parse
argument_list|(
name|response
operator|.
name|getBody
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

