begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|spl
package|package
name|spl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
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
name|FileInputStream
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
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|beans
operator|.
name|Author
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|beans
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|creator
operator|.
name|AuthorBeanCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|creator
operator|.
name|AuthorsBeanCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|creator
operator|.
name|DefaultStringCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|creator
operator|.
name|DocumentBeanCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|creator
operator|.
name|DocumentsBeanCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|creator
operator|.
name|ObjectCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|creator
operator|.
name|TitleBeanCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|creator
operator|.
name|YearBeanCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|reader
operator|.
name|ObjectCreatorMapper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|deserialize
operator|.
name|reader
operator|.
name|XmlResourceReader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|formatter
operator|.
name|Bean
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|formatter
operator|.
name|SimpleTypeElementBean
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|client
operator|.
name|Client
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|client
operator|.
name|ClientResponse
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|client
operator|.
name|WebResource
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|multipart
operator|.
name|FormDataMultiPart
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: Christoph Arbeit  * Date: 09.09.2010  * Time: 10:35:20  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|SplWebClient
specifier|public
class|class
name|SplWebClient
block|{
DECL|field|CLIENT
specifier|private
specifier|static
specifier|final
name|Client
name|CLIENT
init|=
name|Client
operator|.
name|create
argument_list|()
decl_stmt|;
static|static
block|{
name|SplWebClient
operator|.
name|CLIENT
operator|.
name|setConnectTimeout
argument_list|(
literal|1000
argument_list|)
expr_stmt|;
name|SplWebClient
operator|.
name|CLIENT
operator|.
name|setReadTimeout
argument_list|(
literal|70000
argument_list|)
expr_stmt|;
block|}
DECL|field|WEBRESOURCE
specifier|private
specifier|static
specifier|final
name|WebResource
name|WEBRESOURCE
init|=
name|SplWebClient
operator|.
name|CLIENT
operator|.
name|resource
argument_list|(
literal|"http://api.mr-dlib.org/"
argument_list|)
decl_stmt|;
DECL|field|INTERNETRESOURCE
specifier|private
specifier|static
specifier|final
name|WebResource
name|INTERNETRESOURCE
init|=
name|SplWebClient
operator|.
name|CLIENT
operator|.
name|resource
argument_list|(
literal|"http://www.google.com"
argument_list|)
decl_stmt|;
comment|//private static WebResource WEBRESOURCE = CLIENT.resource( "http://localhost:8080/rest/" );
DECL|field|metadata
specifier|public
specifier|static
name|Document
name|metadata
decl_stmt|;
DECL|method|getMetaData (File file)
specifier|public
specifier|static
name|WebServiceStatus
name|getMetaData
parameter_list|(
name|File
name|file
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
operator|!
name|SplWebClient
operator|.
name|isWebServiceAvailable
argument_list|()
condition|)
block|{
if|if
condition|(
name|SplWebClient
operator|.
name|isInternetAvailable
argument_list|()
condition|)
block|{
return|return
name|WebServiceStatus
operator|.
name|WEBSERVICE_DOWN
return|;
block|}
else|else
block|{
return|return
name|WebServiceStatus
operator|.
name|NO_INTERNET
return|;
block|}
block|}
if|if
condition|(
name|SplWebClient
operator|.
name|isWebServiceOutDated
argument_list|()
condition|)
block|{
return|return
name|WebServiceStatus
operator|.
name|OUTDATED
return|;
block|}
if|if
condition|(
operator|!
name|SplWebClient
operator|.
name|isMetaDataServiceAvailable
argument_list|()
condition|)
block|{
return|return
name|WebServiceStatus
operator|.
name|UNAVAILABLE
return|;
block|}
name|FileInputStream
name|fin
init|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|byte
index|[]
name|data
init|=
operator|new
name|byte
index|[
operator|(
name|int
operator|)
name|file
operator|.
name|length
argument_list|()
index|]
decl_stmt|;
name|fin
operator|.
name|read
argument_list|(
name|data
argument_list|)
expr_stmt|;
name|FormDataMultiPart
name|formDataMultiPart
init|=
operator|new
name|FormDataMultiPart
argument_list|()
decl_stmt|;
name|formDataMultiPart
operator|.
name|field
argument_list|(
literal|"file"
argument_list|,
name|data
argument_list|,
name|MediaType
operator|.
name|APPLICATION_OCTET_STREAM_TYPE
argument_list|)
expr_stmt|;
name|formDataMultiPart
operator|.
name|field
argument_list|(
literal|"source"
argument_list|,
literal|"jabref"
argument_list|,
name|MediaType
operator|.
name|TEXT_PLAIN_TYPE
argument_list|)
expr_stmt|;
name|formDataMultiPart
operator|.
name|field
argument_list|(
literal|"filename"
argument_list|,
name|file
operator|.
name|getName
argument_list|()
argument_list|,
name|MediaType
operator|.
name|TEXT_PLAIN_TYPE
argument_list|)
expr_stmt|;
name|ClientResponse
name|response
init|=
name|SplWebClient
operator|.
name|WEBRESOURCE
operator|.
name|path
argument_list|(
literal|"documents"
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|MULTIPART_FORM_DATA_TYPE
argument_list|)
operator|.
name|post
argument_list|(
name|ClientResponse
operator|.
name|class
argument_list|,
name|formDataMultiPart
argument_list|)
decl_stmt|;
comment|//System.out.println(response.getEntity(String.class));
if|if
condition|(
operator|(
name|response
operator|.
name|getClientResponseStatus
argument_list|()
operator|==
name|ClientResponse
operator|.
name|Status
operator|.
name|OK
operator|)
operator|&&
name|response
operator|.
name|hasEntity
argument_list|()
condition|)
block|{
name|String
name|entity
init|=
name|response
operator|.
name|getEntity
argument_list|(
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
name|byte
index|[]
name|bytes
init|=
operator|new
name|byte
index|[
literal|0
index|]
decl_stmt|;
try|try
block|{
name|bytes
operator|=
name|entity
operator|.
name|getBytes
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
name|InputStream
name|is
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|bytes
argument_list|)
decl_stmt|;
if|if
condition|(
name|is
operator|!=
literal|null
condition|)
block|{
name|ObjectCreatorMapper
name|resourceMapper
init|=
operator|new
name|ObjectCreatorMapper
argument_list|()
decl_stmt|;
name|ObjectCreator
name|stringCreator
init|=
operator|new
name|DefaultStringCreator
argument_list|()
decl_stmt|;
comment|// initialize Mapper
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"documents"
argument_list|,
operator|new
name|DocumentsBeanCreator
argument_list|()
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"authors"
argument_list|,
operator|new
name|AuthorsBeanCreator
argument_list|()
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"document"
argument_list|,
operator|new
name|DocumentBeanCreator
argument_list|()
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"title"
argument_list|,
operator|new
name|TitleBeanCreator
argument_list|()
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"year"
argument_list|,
operator|new
name|YearBeanCreator
argument_list|()
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"author"
argument_list|,
operator|new
name|AuthorBeanCreator
argument_list|()
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"name_first"
argument_list|,
name|stringCreator
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"name_middle"
argument_list|,
name|stringCreator
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"name_last"
argument_list|,
name|stringCreator
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"name_last_prefix"
argument_list|,
name|stringCreator
argument_list|)
expr_stmt|;
name|resourceMapper
operator|.
name|addCreator
argument_list|(
literal|"name_last_suffix"
argument_list|,
name|stringCreator
argument_list|)
expr_stmt|;
comment|// initialize xml reader
name|XmlResourceReader
argument_list|<
name|?
argument_list|>
name|reader
init|=
operator|new
name|XmlResourceReader
argument_list|(
name|resourceMapper
argument_list|)
decl_stmt|;
comment|// parse given file -> create object tree
name|Document
name|docs
init|=
operator|(
name|Document
operator|)
name|reader
operator|.
name|parse
argument_list|(
name|is
argument_list|)
decl_stmt|;
for|for
control|(
name|Bean
name|author
range|:
name|docs
operator|.
name|getAuthors
argument_list|()
operator|.
name|getCollection
argument_list|()
control|)
block|{
name|Author
name|temp
init|=
operator|(
name|Author
operator|)
name|author
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
operator|(
operator|(
name|SimpleTypeElementBean
operator|)
name|temp
operator|.
name|getName_Last
argument_list|()
operator|)
operator|.
name|getValue
argument_list|()
operator|+
literal|' '
operator|+
name|temp
operator|.
name|getRank
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// XmlDocuments documents = JAXB.unmarshal(is, XmlDocuments.class);
name|SplWebClient
operator|.
name|metadata
operator|=
name|docs
expr_stmt|;
return|return
name|WebServiceStatus
operator|.
name|OK
return|;
block|}
else|else
block|{
return|return
name|WebServiceStatus
operator|.
name|NO_METADATA
return|;
block|}
block|}
if|if
condition|(
name|response
operator|.
name|getClientResponseStatus
argument_list|()
operator|==
name|ClientResponse
operator|.
name|Status
operator|.
name|SERVICE_UNAVAILABLE
condition|)
block|{
return|return
name|WebServiceStatus
operator|.
name|UNAVAILABLE
return|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Tools
operator|.
name|getStackTraceAsString
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
comment|//Todo logging
block|}
return|return
name|WebServiceStatus
operator|.
name|NO_METADATA
return|;
block|}
DECL|method|isWebServiceOutDated ()
specifier|private
specifier|static
name|boolean
name|isWebServiceOutDated
parameter_list|()
block|{
try|try
block|{
name|ClientResponse
name|response
init|=
name|SplWebClient
operator|.
name|WEBRESOURCE
operator|.
name|path
argument_list|(
literal|"service/versioncheck/"
operator|+
name|Tools
operator|.
name|WEBSERVICE_APP_ID
operator|+
literal|"/current"
argument_list|)
operator|.
name|get
argument_list|(
name|ClientResponse
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|response
operator|.
name|getClientResponseStatus
argument_list|()
operator|==
name|ClientResponse
operator|.
name|Status
operator|.
name|OK
operator|)
operator|&&
name|response
operator|.
name|hasEntity
argument_list|()
condition|)
block|{
name|String
name|entity
init|=
name|response
operator|.
name|getEntity
argument_list|(
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
name|byte
index|[]
name|bytes
init|=
name|entity
operator|.
name|getBytes
argument_list|()
decl_stmt|;
name|InputStream
name|is
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|bytes
argument_list|)
decl_stmt|;
if|if
condition|(
name|is
operator|!=
literal|null
condition|)
block|{
comment|/*XmlApplication app = JAXB.unmarshal(is, XmlApplication.class);                     if(app != null){                         if(app.getVersion() != null&& !app.getVersion().equalsIgnoreCase(Tools.WEBSERVICE_VERSION_SHORT)){                             return true;                         }                     }*/
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|//Todo logging
block|}
return|return
literal|false
return|;
block|}
DECL|method|isMetaDataServiceAvailable ()
specifier|private
specifier|static
name|boolean
name|isMetaDataServiceAvailable
parameter_list|()
block|{
try|try
block|{
name|ClientResponse
name|response
init|=
name|SplWebClient
operator|.
name|WEBRESOURCE
operator|.
name|path
argument_list|(
literal|"service/metadata/available"
argument_list|)
operator|.
name|get
argument_list|(
name|ClientResponse
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|response
operator|.
name|getClientResponseStatus
argument_list|()
operator|==
name|ClientResponse
operator|.
name|Status
operator|.
name|OK
operator|)
operator|&&
name|response
operator|.
name|hasEntity
argument_list|()
condition|)
block|{
name|String
name|entity
init|=
name|response
operator|.
name|getEntity
argument_list|(
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|entity
operator|!=
literal|null
operator|)
operator|&&
name|entity
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"false"
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|//Todo logging
block|}
return|return
literal|true
return|;
block|}
DECL|method|isWebServiceAvailable ()
specifier|private
specifier|static
name|boolean
name|isWebServiceAvailable
parameter_list|()
block|{
try|try
block|{
name|ClientResponse
name|response
init|=
name|SplWebClient
operator|.
name|WEBRESOURCE
operator|.
name|path
argument_list|(
literal|"service/metadata/available"
argument_list|)
operator|.
name|get
argument_list|(
name|ClientResponse
operator|.
name|class
argument_list|)
decl_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|isInternetAvailable ()
specifier|private
specifier|static
name|boolean
name|isInternetAvailable
parameter_list|()
block|{
try|try
block|{
name|ClientResponse
name|response
init|=
name|SplWebClient
operator|.
name|INTERNETRESOURCE
operator|.
name|get
argument_list|(
name|ClientResponse
operator|.
name|class
argument_list|)
decl_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
DECL|enum|WebServiceStatus
specifier|public
enum|enum
name|WebServiceStatus
block|{
DECL|enumConstant|OK
name|OK
block|,
DECL|enumConstant|NO_METADATA
name|NO_METADATA
block|,
DECL|enumConstant|UNAVAILABLE
name|UNAVAILABLE
block|,
DECL|enumConstant|OUTDATED
name|OUTDATED
block|,
DECL|enumConstant|WEBSERVICE_DOWN
name|WEBSERVICE_DOWN
block|,
DECL|enumConstant|NO_INTERNET
name|NO_INTERNET
block|}
block|}
end_class

end_unit

