begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  * License: GPLv2, but Jan Frederik Maas agreed to change license upon request  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fetcher
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
name|io
operator|.
name|UnsupportedEncodingException
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
name|net
operator|.
name|URLEncoder
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
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|importer
operator|.
name|ImportInspector
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
name|OutputPrinter
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
name|l10n
operator|.
name|Localization
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
comment|/**  * Fetch or search from GVK http://gso.gbv.de/sru/DB=2.1/  */
end_comment

begin_class
DECL|class|GVKFetcher
specifier|public
class|class
name|GVKFetcher
implements|implements
name|EntryFetcher
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
name|GVKFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|searchKeys
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchKeys
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|GVKFetcher ()
specifier|public
name|GVKFetcher
parameter_list|()
block|{
name|searchKeys
operator|.
name|put
argument_list|(
literal|"all"
argument_list|,
literal|"pica.all%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"tit"
argument_list|,
literal|"pica.tit%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"per"
argument_list|,
literal|"pica.per%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"thm"
argument_list|,
literal|"pica.thm%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"slw"
argument_list|,
literal|"pica.slw%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"txt"
argument_list|,
literal|"pica.txt%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"num"
argument_list|,
literal|"pica.num%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"kon"
argument_list|,
literal|"pica.kon%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"ppn"
argument_list|,
literal|"pica.ppn%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"bkl"
argument_list|,
literal|"pica.bkl%3D"
argument_list|)
expr_stmt|;
name|searchKeys
operator|.
name|put
argument_list|(
literal|"erj"
argument_list|,
literal|"pica.erj%3D"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Necessary for JabRef      */
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
comment|// not supported
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
block|{
return|return
literal|"GVKHelp"
return|;
block|}
annotation|@
name|Override
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
literal|"GVK (Gemeinsamer Verbundkatalog)"
return|;
block|}
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector dialog, OutputPrinter frame)
specifier|public
name|boolean
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|dialog
parameter_list|,
name|OutputPrinter
name|frame
parameter_list|)
block|{
name|query
operator|=
name|query
operator|.
name|trim
argument_list|()
expr_stmt|;
name|String
index|[]
name|qterms
init|=
name|query
operator|.
name|split
argument_list|(
literal|"\\s"
argument_list|)
decl_stmt|;
comment|// Null abfangen!
if|if
condition|(
name|qterms
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// Jeden einzelnen Suchbegriff URL-Encodieren
for|for
control|(
name|int
name|x
init|=
literal|0
init|;
name|x
operator|<
name|qterms
operator|.
name|length
condition|;
name|x
operator|++
control|)
block|{
try|try
block|{
name|qterms
index|[
name|x
index|]
operator|=
name|URLEncoder
operator|.
name|encode
argument_list|(
name|qterms
index|[
name|x
index|]
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Unsupported encoding"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|gvkQuery
decl_stmt|;
if|if
condition|(
name|searchKeys
operator|.
name|containsKey
argument_list|(
name|qterms
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
name|gvkQuery
operator|=
name|processComplexQuery
argument_list|(
name|qterms
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|gvkQuery
operator|=
literal|"pica.all%3D"
expr_stmt|;
name|gvkQuery
operator|=
name|gvkQuery
operator|.
name|concat
argument_list|(
name|qterms
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|x
init|=
literal|1
init|;
name|x
operator|<
name|qterms
operator|.
name|length
condition|;
name|x
operator|++
control|)
block|{
name|gvkQuery
operator|=
name|gvkQuery
operator|.
name|concat
argument_list|(
literal|"%20"
argument_list|)
expr_stmt|;
name|gvkQuery
operator|=
name|gvkQuery
operator|.
name|concat
argument_list|(
name|qterms
index|[
name|x
index|]
argument_list|)
expr_stmt|;
block|}
block|}
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibs
init|=
name|fetchGVK
argument_list|(
name|gvkQuery
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|bibs
control|)
block|{
name|dialog
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|bibs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|frame
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No references found"
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|processComplexQuery (String[] s)
specifier|private
name|String
name|processComplexQuery
parameter_list|(
name|String
index|[]
name|s
parameter_list|)
block|{
name|String
name|result
init|=
literal|""
decl_stmt|;
name|boolean
name|lastWasKey
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|x
init|=
literal|0
init|;
name|x
operator|<
name|s
operator|.
name|length
condition|;
name|x
operator|++
control|)
block|{
if|if
condition|(
name|searchKeys
operator|.
name|containsKey
argument_list|(
name|s
index|[
name|x
index|]
argument_list|)
condition|)
block|{
if|if
condition|(
name|x
operator|==
literal|0
condition|)
block|{
name|result
operator|=
name|searchKeys
operator|.
name|get
argument_list|(
name|s
index|[
name|x
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|=
name|result
operator|.
name|concat
argument_list|(
literal|"%20and%20"
operator|+
name|searchKeys
operator|.
name|get
argument_list|(
name|s
index|[
name|x
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|lastWasKey
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|lastWasKey
condition|)
block|{
name|result
operator|=
name|result
operator|.
name|concat
argument_list|(
literal|"%20"
argument_list|)
expr_stmt|;
block|}
name|String
name|encoded
init|=
name|s
index|[
name|x
index|]
decl_stmt|;
name|encoded
operator|=
name|encoded
operator|.
name|replace
argument_list|(
literal|","
argument_list|,
literal|"%2C"
argument_list|)
operator|.
name|replace
argument_list|(
literal|"?"
argument_list|,
literal|"%3F"
argument_list|)
expr_stmt|;
name|result
operator|=
name|result
operator|.
name|concat
argument_list|(
name|encoded
argument_list|)
expr_stmt|;
name|lastWasKey
operator|=
literal|false
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|method|fetchGVK (String query)
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchGVK
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|result
decl_stmt|;
name|String
name|urlPrefix
init|=
literal|"http://sru.gbv.de/gvk?version=1.1&operation=searchRetrieve&query="
decl_stmt|;
name|String
name|urlSuffix
init|=
literal|"&maximumRecords=50&recordSchema=picaxml&sortKeys=Year%2C%2C1"
decl_stmt|;
name|String
name|searchstring
init|=
name|urlPrefix
operator|+
name|query
operator|+
name|urlSuffix
decl_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
name|searchstring
argument_list|)
expr_stmt|;
try|try
block|{
name|URI
name|uri
init|=
operator|new
name|URI
argument_list|(
name|searchstring
argument_list|)
decl_stmt|;
name|URL
name|url
init|=
name|uri
operator|.
name|toURL
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStream
name|is
init|=
name|url
operator|.
name|openStream
argument_list|()
init|)
block|{
name|result
operator|=
operator|(
operator|new
name|GVKParser
argument_list|()
operator|)
operator|.
name|parseEntries
argument_list|(
name|is
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"URI malformed error"
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
literal|"GVK: An I/O exception occurred"
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
catch|catch
parameter_list|(
name|ParserConfigurationException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"GVK: An internal parser error occurred"
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
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"An internal parser error occurred"
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
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

