begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|List
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
name|SAXParser
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
name|SAXParserFactory
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

begin_comment
comment|/**  * Importer for the Refer/Endnote format.  *  * check here for details on the format  * http://www.ecst.csuchico.edu/~jacobsd/bib/formats/endnote.html  */
end_comment

begin_class
DECL|class|MedlineImporter
specifier|public
class|class
name|MedlineImporter
extends|extends
name|ImportFormat
block|{
comment|/**      * Return the name of this import format.      */
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"Medline"
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#getCLIId()      */
DECL|method|getCLIId ()
specifier|public
name|String
name|getCLIId
parameter_list|()
block|{
return|return
literal|"medline"
return|;
block|}
comment|/**      * Check whether the source is in the correct format for this importer.      */
DECL|method|isRecognizedFormat (InputStream in)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
return|return
literal|true
return|;
block|}
comment|/**      * Parse the entries in the source, and return a List of BibtexEntry      * objects.      */
DECL|method|importEntries (InputStream stream)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|stream
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Obtain a factory object for creating SAX parsers
name|SAXParserFactory
name|parserFactory
init|=
name|SAXParserFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
comment|// Configure the factory object to specify attributes of the parsers it
comment|// creates
name|parserFactory
operator|.
name|setValidating
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|parserFactory
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// Now create a SAXParser object
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|bibItems
init|=
literal|null
decl_stmt|;
try|try
block|{
name|SAXParser
name|parser
init|=
name|parserFactory
operator|.
name|newSAXParser
argument_list|()
decl_stmt|;
comment|//May throw exceptions
name|MedlineHandler
name|handler
init|=
operator|new
name|MedlineHandler
argument_list|()
decl_stmt|;
comment|// Start the parser. It reads the file and calls methods of the handler.
name|parser
operator|.
name|parse
argument_list|(
name|stream
argument_list|,
name|handler
argument_list|)
expr_stmt|;
comment|// When you're done, report the results stored by your handler object
name|bibItems
operator|=
name|handler
operator|.
name|getItems
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
name|e1
parameter_list|)
block|{ 	}
catch|catch
parameter_list|(
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
name|e2
parameter_list|)
block|{ 	}
catch|catch
parameter_list|(
name|java
operator|.
name|io
operator|.
name|IOException
name|e3
parameter_list|)
block|{ 	}
return|return
name|bibItems
return|;
block|}
block|}
end_class

end_unit

