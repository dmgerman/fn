begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Created on Jun 13, 2004  *  * To change the template for this generated file go to  * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments  */
end_comment

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
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Insets
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
name|net
operator|.
name|HttpURLConnection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JProgressBar
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|httpclient
operator|.
name|HttpClient
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
name|httpclient
operator|.
name|HttpException
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
name|httpclient
operator|.
name|methods
operator|.
name|GetMethod
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * @author mspiegel  *  * To change the template for this generated type comment go to  * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments  */
end_comment

begin_class
DECL|class|CiteSeerFetcher
specifier|public
class|class
name|CiteSeerFetcher
extends|extends
name|SidePaneComponent
block|{
DECL|field|PREFIX_URL
specifier|final
name|String
name|PREFIX_URL
init|=
literal|"http://citeseer.ist.psu.edu/"
decl_stmt|;
DECL|field|PREFIX_IDENTIFIER
specifier|final
name|String
name|PREFIX_IDENTIFIER
init|=
literal|"oai:CiteSeerPSU:"
decl_stmt|;
DECL|field|OAI_HOST
specifier|final
name|String
name|OAI_HOST
init|=
literal|"http://cs1.ist.psu.edu/"
decl_stmt|;
DECL|field|OAI_URL
specifier|final
name|String
name|OAI_URL
init|=
name|OAI_HOST
operator|+
literal|"cgi-bin/oai.cgi?"
decl_stmt|;
DECL|field|OAI_ACTION
specifier|final
name|String
name|OAI_ACTION
init|=
literal|"verb=GetRecord"
decl_stmt|;
DECL|field|OAI_METADATAPREFIX
specifier|final
name|String
name|OAI_METADATAPREFIX
init|=
literal|"metadataPrefix=oai_citeseer"
decl_stmt|;
DECL|field|parserFactory
specifier|protected
name|SAXParserFactory
name|parserFactory
decl_stmt|;
DECL|field|saxParser
specifier|protected
name|SAXParser
name|saxParser
decl_stmt|;
DECL|field|citeseerCon
specifier|protected
name|HttpURLConnection
name|citeseerCon
decl_stmt|;
DECL|field|citeseerHttpClient
specifier|protected
name|HttpClient
name|citeseerHttpClient
decl_stmt|;
DECL|field|fetcherActive
name|boolean
name|fetcherActive
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|progressBar
name|JProgressBar
name|progressBar
decl_stmt|;
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|sidePaneManager
name|SidePaneManager
name|sidePaneManager
decl_stmt|;
DECL|method|CiteSeerFetcher (BasePanel panel_, SidePaneManager p0)
specifier|public
name|CiteSeerFetcher
parameter_list|(
name|BasePanel
name|panel_
parameter_list|,
name|SidePaneManager
name|p0
parameter_list|)
block|{
name|super
argument_list|(
name|p0
argument_list|)
expr_stmt|;
name|panel
operator|=
name|panel_
expr_stmt|;
name|sidePaneManager
operator|=
name|p0
expr_stmt|;
name|progressBar
operator|=
operator|new
name|JProgressBar
argument_list|()
expr_stmt|;
name|progressBar
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|progressBar
operator|.
name|setValue
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|progressBar
operator|.
name|setMinimum
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|progressBar
operator|.
name|setStringPainted
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//		I can't make this panel re-appear!
comment|//      See comment "Ensure visible does not appear to be working" in JabRefFrame.java
comment|//		sidePaneManager.hideAway(this);
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|SidePaneHeader
name|header
init|=
operator|new
name|SidePaneHeader
argument_list|(
literal|"CiteSeer Transfer"
argument_list|,
name|GUIGlobals
operator|.
name|fetchHourglassIcon
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|header
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|header
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|progressBar
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|progressBar
argument_list|)
expr_stmt|;
try|try
block|{
name|fetcherActive
operator|=
literal|false
expr_stmt|;
name|parserFactory
operator|=
name|SAXParserFactory
operator|.
name|newInstance
argument_list|()
expr_stmt|;
name|saxParser
operator|=
name|parserFactory
operator|.
name|newSAXParser
argument_list|()
expr_stmt|;
name|citeseerHttpClient
operator|=
operator|new
name|HttpClient
argument_list|()
expr_stmt|;
name|citeseerHttpClient
operator|.
name|setConnectionTimeout
argument_list|(
literal|10000
argument_list|)
expr_stmt|;
comment|// 10 seconds
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/***********************************/
comment|/* Begin Inner Class Declarations */
comment|/* The inner classes are used to modify components, when not in the 	 * event-dispatching thread.  These are used to follow the "single-threaded 	 * rule", as defined here: http://java.sun.com/products/jfc/tsc/articles/threads/threads1.html 	 * 	 * 	 * I'm pretty sure the Dialog box invokers should remain as inner classes, 	 * but I can't decide whether or not to break the one-thread rule for the 	 * progress bar classes.  Because the search contains a locking-mechanism, 	 * activateFetcher() and deactivateFetcher(), there should only be at-most-one 	 * thread accessing the progress bar at any time. 	 */
DECL|class|ShowNoConnectionDialog
class|class
name|ShowNoConnectionDialog
implements|implements
name|Runnable
block|{
DECL|field|targetURL
specifier|protected
name|String
name|targetURL
init|=
literal|""
decl_stmt|;
DECL|method|ShowNoConnectionDialog (String URL)
name|ShowNoConnectionDialog
parameter_list|(
name|String
name|URL
parameter_list|)
block|{
name|targetURL
operator|=
name|URL
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
literal|"I could not connect to host "
operator|+
name|targetURL
operator|+
literal|".  Please check your network connection "
operator|+
literal|"to this machine."
argument_list|,
literal|"CiteSeer Connection Error"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|ShowBadURLDialog
class|class
name|ShowBadURLDialog
implements|implements
name|Runnable
block|{
DECL|field|badURL
specifier|protected
name|String
name|badURL
init|=
literal|""
decl_stmt|;
DECL|method|ShowBadURLDialog (String URL)
name|ShowBadURLDialog
parameter_list|(
name|String
name|URL
parameter_list|)
block|{
name|badURL
operator|=
name|URL
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
literal|"I couldn't parse the following URL: "
operator|+
name|badURL
operator|+
comment|// How do I retrieve the index number? NOT the BibTex ID.
literal|" on entry number XXX.  "
operator|+
literal|" Please refer to the JabRef help manual on using the CiteSeer tools."
argument_list|,
literal|"CiteSeer Error"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|UpdateProgressBarMaximum
class|class
name|UpdateProgressBarMaximum
implements|implements
name|Runnable
block|{
DECL|field|maximum
specifier|protected
name|int
name|maximum
decl_stmt|;
DECL|method|UpdateProgressBarMaximum (int newValue)
name|UpdateProgressBarMaximum
parameter_list|(
name|int
name|newValue
parameter_list|)
block|{
name|maximum
operator|=
name|newValue
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|progressBar
operator|.
name|setMaximum
argument_list|(
name|maximum
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|UpdateProgressBarValue
class|class
name|UpdateProgressBarValue
implements|implements
name|Runnable
block|{
DECL|field|counter
specifier|protected
name|int
name|counter
decl_stmt|;
DECL|method|UpdateProgressBarValue (int newValue)
name|UpdateProgressBarValue
parameter_list|(
name|int
name|newValue
parameter_list|)
block|{
name|counter
operator|=
name|newValue
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|progressBar
operator|.
name|setValue
argument_list|(
name|counter
argument_list|)
expr_stmt|;
block|}
block|}
comment|/* End Inner Class Declarations */
comment|/***********************************/
DECL|method|activateFetcher ()
specifier|synchronized
specifier|public
name|boolean
name|activateFetcher
parameter_list|()
block|{
if|if
condition|(
name|fetcherActive
operator|==
literal|true
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
else|else
block|{
name|fetcherActive
operator|=
literal|true
expr_stmt|;
return|return
operator|(
literal|true
operator|)
return|;
block|}
block|}
DECL|method|deactivateFetcher ()
specifier|synchronized
specifier|public
name|void
name|deactivateFetcher
parameter_list|()
block|{
name|fetcherActive
operator|=
literal|false
expr_stmt|;
block|}
comment|/** 	 * @param newDatabase 	 * @param targetDatabase 	 */
DECL|method|populate (BibtexDatabase newDatabase, BibtexDatabase targetDatabase)
specifier|public
name|void
name|populate
parameter_list|(
name|BibtexDatabase
name|newDatabase
parameter_list|,
name|BibtexDatabase
name|targetDatabase
parameter_list|)
block|{
name|Iterator
name|targetIterator
init|=
name|targetDatabase
operator|.
name|getKeySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|String
name|currentKey
decl_stmt|;
name|BibtexEntry
name|currentEntry
decl_stmt|;
name|Enumeration
name|newEntryEnum
decl_stmt|;
name|Hashtable
name|citationHashTable
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|UpdateProgressBarValue
name|updateValue
init|=
operator|new
name|UpdateProgressBarValue
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|updateValue
argument_list|)
expr_stmt|;
while|while
condition|(
name|targetIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|currentKey
operator|=
operator|(
name|String
operator|)
name|targetIterator
operator|.
name|next
argument_list|()
expr_stmt|;
name|currentEntry
operator|=
name|targetDatabase
operator|.
name|getEntryById
argument_list|(
name|currentKey
argument_list|)
expr_stmt|;
name|generateIdentifierList
argument_list|(
name|currentEntry
argument_list|,
name|citationHashTable
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|citationHashTable
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|UpdateProgressBarMaximum
name|updateMaximum
init|=
operator|new
name|UpdateProgressBarMaximum
argument_list|(
name|citationHashTable
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|updateMaximum
argument_list|)
expr_stmt|;
block|}
name|generateCitationList
argument_list|(
name|citationHashTable
argument_list|,
name|newDatabase
argument_list|)
expr_stmt|;
name|newEntryEnum
operator|=
name|citationHashTable
operator|.
name|elements
argument_list|()
expr_stmt|;
while|while
condition|(
name|newEntryEnum
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
try|try
block|{
name|BibtexEntry
name|nextEntry
init|=
operator|(
name|BibtexEntry
operator|)
name|newEntryEnum
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|newDatabase
operator|.
name|insertEntry
argument_list|(
name|nextEntry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{ 			}
block|}
block|}
DECL|method|generateCitationList (Hashtable citationHashTable, BibtexDatabase database)
specifier|private
name|Hashtable
name|generateCitationList
parameter_list|(
name|Hashtable
name|citationHashTable
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
operator|(
name|citationHashTable
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|citationHashTable
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|int
name|citationCounter
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|citationHashTable
operator|.
name|keys
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|key
init|=
operator|(
name|String
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|String
name|id
init|=
name|Util
operator|.
name|createId
argument_list|(
name|BibtexEntryType
operator|.
name|ARTICLE
argument_list|,
name|database
argument_list|)
decl_stmt|;
name|BibtexEntry
name|newEntry
init|=
operator|new
name|BibtexEntry
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|StringBuffer
name|citeseerURLString
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
name|OAI_URL
argument_list|)
expr_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
name|OAI_ACTION
argument_list|)
expr_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
literal|"&"
operator|+
name|OAI_METADATAPREFIX
argument_list|)
expr_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
literal|"&"
operator|+
literal|"identifier="
operator|+
name|key
argument_list|)
expr_stmt|;
name|GetMethod
name|citeseerMethod
init|=
operator|new
name|GetMethod
argument_list|(
name|citeseerURLString
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|citeseerHttpClient
operator|.
name|executeMethod
argument_list|(
name|citeseerMethod
argument_list|)
expr_stmt|;
name|saxParser
operator|.
name|parse
argument_list|(
name|citeseerMethod
operator|.
name|getResponseBodyAsStream
argument_list|()
argument_list|,
operator|new
name|CiteSeerImportHandler
argument_list|(
name|newEntry
argument_list|)
argument_list|)
expr_stmt|;
name|citeseerMethod
operator|.
name|releaseConnection
argument_list|()
expr_stmt|;
name|citationHashTable
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|newEntry
argument_list|)
expr_stmt|;
name|citationCounter
operator|++
expr_stmt|;
name|UpdateProgressBarValue
name|updateValue
init|=
operator|new
name|UpdateProgressBarValue
argument_list|(
name|citationCounter
argument_list|)
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|updateValue
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|HttpException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"HttpException: "
operator|+
name|e
operator|.
name|getReason
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"SAXException: "
operator|+
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|ShowNoConnectionDialog
name|dialog
init|=
operator|new
name|ShowNoConnectionDialog
argument_list|(
name|OAI_HOST
argument_list|)
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|dialog
argument_list|)
expr_stmt|;
block|}
return|return
name|citationHashTable
return|;
block|}
DECL|method|generateIdentifierList (BibtexEntry currentEntry, Hashtable citationHashTable)
specifier|private
name|void
name|generateIdentifierList
parameter_list|(
name|BibtexEntry
name|currentEntry
parameter_list|,
name|Hashtable
name|citationHashTable
parameter_list|)
block|{
name|String
name|targetURL
init|=
operator|(
name|String
operator|)
name|currentEntry
operator|.
name|getField
argument_list|(
literal|"url"
argument_list|)
decl_stmt|;
try|try
block|{
if|if
condition|(
name|targetURL
operator|!=
literal|null
operator|&&
name|targetURL
operator|.
name|startsWith
argument_list|(
name|PREFIX_URL
argument_list|)
operator|&&
operator|(
name|targetURL
operator|.
name|length
argument_list|()
operator|>
operator|(
name|PREFIX_URL
operator|.
name|length
argument_list|()
operator|+
literal|5
operator|)
operator|)
condition|)
block|{
name|String
name|id
init|=
name|targetURL
operator|.
name|substring
argument_list|(
name|PREFIX_URL
operator|.
name|length
argument_list|()
argument_list|,
name|targetURL
operator|.
name|length
argument_list|()
operator|-
literal|5
argument_list|)
decl_stmt|;
name|String
name|identifier
init|=
name|PREFIX_IDENTIFIER
operator|+
name|id
decl_stmt|;
name|StringBuffer
name|citeseerURLString
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
name|OAI_URL
argument_list|)
expr_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
name|OAI_ACTION
argument_list|)
expr_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
literal|"&"
operator|+
name|OAI_METADATAPREFIX
argument_list|)
expr_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
literal|"&"
operator|+
literal|"identifier="
operator|+
name|identifier
argument_list|)
expr_stmt|;
name|GetMethod
name|citeseerMethod
init|=
operator|new
name|GetMethod
argument_list|(
name|citeseerURLString
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|citeseerHttpClient
operator|.
name|executeMethod
argument_list|(
name|citeseerMethod
argument_list|)
expr_stmt|;
name|saxParser
operator|.
name|parse
argument_list|(
name|citeseerMethod
operator|.
name|getResponseBodyAsStream
argument_list|()
argument_list|,
operator|new
name|CiteSeerCitationHandler
argument_list|(
name|citationHashTable
argument_list|)
argument_list|)
expr_stmt|;
name|citeseerMethod
operator|.
name|releaseConnection
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|ShowBadURLDialog
name|dialog
init|=
operator|new
name|ShowBadURLDialog
argument_list|(
name|targetURL
argument_list|)
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|dialog
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|HttpException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"HttpException: "
operator|+
name|e
operator|.
name|getReason
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"SAXException: "
operator|+
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|ShowNoConnectionDialog
name|dialog
init|=
operator|new
name|ShowNoConnectionDialog
argument_list|(
name|OAI_HOST
argument_list|)
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|dialog
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * @param be 	 * 	 * Reminder: this method runs in the EventDispatcher thread 	 */
DECL|method|importCiteSeerEntry (BibtexEntry be)
specifier|public
name|boolean
name|importCiteSeerEntry
parameter_list|(
name|BibtexEntry
name|be
parameter_list|)
block|{
name|boolean
name|newValue
init|=
literal|false
decl_stmt|;
name|SAXParserFactory
name|factory
init|=
name|SAXParserFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|String
name|targetURL
init|=
operator|(
name|String
operator|)
name|be
operator|.
name|getField
argument_list|(
literal|"url"
argument_list|)
decl_stmt|;
try|try
block|{
if|if
condition|(
name|targetURL
operator|!=
literal|null
operator|&&
name|targetURL
operator|.
name|startsWith
argument_list|(
name|PREFIX_URL
argument_list|)
operator|&&
operator|(
name|targetURL
operator|.
name|length
argument_list|()
operator|>
operator|(
name|PREFIX_URL
operator|.
name|length
argument_list|()
operator|+
literal|5
operator|)
operator|)
condition|)
block|{
name|String
name|id
init|=
name|targetURL
operator|.
name|substring
argument_list|(
name|PREFIX_URL
operator|.
name|length
argument_list|()
argument_list|,
name|targetURL
operator|.
name|length
argument_list|()
operator|-
literal|5
argument_list|)
decl_stmt|;
name|String
name|identifier
init|=
name|PREFIX_IDENTIFIER
operator|+
name|id
decl_stmt|;
name|StringBuffer
name|citeseerURLString
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
name|OAI_URL
argument_list|)
expr_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
name|OAI_ACTION
argument_list|)
expr_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
literal|"&"
operator|+
name|OAI_METADATAPREFIX
argument_list|)
expr_stmt|;
name|citeseerURLString
operator|.
name|append
argument_list|(
literal|"&"
operator|+
literal|"identifier="
operator|+
name|identifier
argument_list|)
expr_stmt|;
name|GetMethod
name|citeseerMethod
init|=
operator|new
name|GetMethod
argument_list|(
name|citeseerURLString
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|response
init|=
name|citeseerHttpClient
operator|.
name|executeMethod
argument_list|(
name|citeseerMethod
argument_list|)
decl_stmt|;
name|saxParser
operator|.
name|parse
argument_list|(
name|citeseerMethod
operator|.
name|getResponseBodyAsStream
argument_list|()
argument_list|,
operator|new
name|CiteSeerImportHandler
argument_list|(
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|citeseerMethod
operator|.
name|releaseConnection
argument_list|()
expr_stmt|;
name|newValue
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|ShowBadURLDialog
name|dialog
init|=
operator|new
name|ShowBadURLDialog
argument_list|(
name|targetURL
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|run
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|HttpException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"HttpException: "
operator|+
name|e
operator|.
name|getReason
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|ShowNoConnectionDialog
name|dialog
init|=
operator|new
name|ShowNoConnectionDialog
argument_list|(
name|OAI_HOST
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|run
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"SAXException: "
operator|+
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
name|newValue
return|;
block|}
block|}
end_class

end_unit

