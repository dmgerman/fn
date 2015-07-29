begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|InputStreamReader
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
name|HttpURLConnection
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
name|JPanel
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
name|BibtexDatabase
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|OutputPrinter
import|;
end_import

begin_comment
comment|/**  *   * This class allows to access the Slac INSPIRE database. It is just a port of  * the original SPIRES Fetcher.  *   * It can either be a GeneralFetcher to pose requests to the database or fetch  * individual entries.  *   * @author Fedor Bezrukov  * @author Sheer El-Showk  *   * @version $Id$  *   */
end_comment

begin_class
DECL|class|INSPIREFetcher
specifier|public
class|class
name|INSPIREFetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|INSPIRE_HOST
specifier|private
specifier|static
specifier|final
name|String
name|INSPIRE_HOST
init|=
literal|"inspirehep.net"
decl_stmt|;
DECL|method|INSPIREFetcher ()
specifier|public
name|INSPIREFetcher
parameter_list|()
block|{     }
comment|/**      * Construct the query URL      *      * NOTE: we truncate at 1000 returned entries but its likely INSPIRE returns      * fewer anyway.  This shouldn't be a problem since users should probably do      * more specific searches.      *       * @param key      *            The key of the OAI2 entry that the url should poitn to.      *       * @return a String denoting the query URL      */
DECL|method|constructUrl (String key)
specifier|private
name|String
name|constructUrl
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|String
name|identifier
decl_stmt|;
try|try
block|{
name|identifier
operator|=
name|URLEncoder
operator|.
name|encode
argument_list|(
name|key
argument_list|,
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
literal|""
return|;
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"http://"
argument_list|)
operator|.
name|append
argument_list|(
name|INSPIREFetcher
operator|.
name|INSPIRE_HOST
argument_list|)
operator|.
name|append
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"/search?ln=en&ln=en&p=find+"
argument_list|)
expr_stmt|;
comment|//sb.append("spires/find/hep/www").append("?");
comment|//sb.append("rawcmd=find+");
name|sb
operator|.
name|append
argument_list|(
name|identifier
argument_list|)
expr_stmt|;
comment|//sb.append("&action_search=Search&sf=&so=d&rm=&rg=25&sc=0&of=hx");
name|sb
operator|.
name|append
argument_list|(
literal|"&action_search=Search&sf=&so=d&rm=&rg=1000&sc=0&of=hx"
argument_list|)
expr_stmt|;
comment|//sb.append("&FORMAT=WWWBRIEFBIBTEX&SEQUENCE=");
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"Inspire URL: "
operator|+
name|sb
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Constructs a INSPIRE query url from slaccitation field      *       * @param slaccitation      * @return query string      *     public static String constructUrlFromSlaccitation(String slaccitation) {     	String cmd = "j";     	String key = slaccitation.replaceAll("^%%CITATION = ", "").replaceAll(     			";%%$", "");     	if (key.matches("^\\w*-\\w*[ /].*"))     		cmd = "eprint";     	try {     		key = URLEncoder.encode(key, "UTF-8");     	} catch (UnsupportedEncodingException e) {     	}     	StringBuffer sb = new StringBuffer("http://").append(INSPIRE_HOST)     			.append("/");     	sb.append("spires/find/hep/www").append("?");     	sb.append("rawcmd=find+").append(cmd).append("+");     	sb.append(key);     	return sb.toString();     }      /**      * Construct an INSPIRE query url from eprint field      *       * @param eprint      * @return query string      *     public static String constructUrlFromEprint(String eprint) {     	String key = eprint.replaceAll(" [.*]$", "");     	try {     		key = URLEncoder.encode(key, "UTF-8");     	} catch (UnsupportedEncodingException e) {     		return "";     	}     	StringBuffer sb = new StringBuffer("http://").append(INSPIRE_HOST)     			.append("/");     	sb.append("spires/find/hep/www").append("?");     	sb.append("rawcmd=find+eprint+");     	sb.append(key);     	return sb.toString();     }*/
comment|/**      * Import an entry from an OAI2 archive. The BibtexEntry provided has to      * have the field OAI2_IDENTIFIER_FIELD set to the search string.      *       * @param key      *            The OAI2 key to fetch from ArXiv.      * @return The imnported BibtexEntry or null if none.      */
DECL|method|importInspireEntries (String key, OutputPrinter frame)
specifier|private
name|BibtexDatabase
name|importInspireEntries
parameter_list|(
name|String
name|key
parameter_list|,
name|OutputPrinter
name|frame
parameter_list|)
block|{
name|String
name|url
init|=
name|constructUrl
argument_list|(
name|key
argument_list|)
decl_stmt|;
try|try
block|{
name|HttpURLConnection
name|conn
init|=
call|(
name|HttpURLConnection
call|)
argument_list|(
operator|new
name|URL
argument_list|(
name|url
argument_list|)
argument_list|)
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|conn
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"Jabref"
argument_list|)
expr_stmt|;
name|InputStream
name|inputStream
init|=
name|conn
operator|.
name|getInputStream
argument_list|()
decl_stmt|;
name|INSPIREBibtexFilterReader
name|reader
init|=
operator|new
name|INSPIREBibtexFilterReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|inputStream
argument_list|)
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|reader
argument_list|)
decl_stmt|;
return|return
name|pr
operator|.
name|getDatabase
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|frame
operator|.
name|showMessage
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"An Exception ocurred while accessing '%0'"
argument_list|,
name|url
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
name|getKeyName
argument_list|()
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|frame
operator|.
name|showMessage
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"An Error occurred while fetching from INSPIRE source (%0):"
argument_list|,
operator|new
name|String
index|[]
block|{
name|url
block|}
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
name|getKeyName
argument_list|()
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
comment|// public void addSpiresURL(BibtexEntry entry) {
comment|// String url = "http://"+spiresHost+"/spires/find/hep/www?texkey+";
comment|// url = url+entry.getCiteKey();
comment|// entry.setField("url", url);
comment|// }
comment|//
comment|// public void addSpiresURLtoDatabase(BibtexDatabase db) {
comment|// Iterator<BibtexEntry> iter = db.getEntries().iterator();
comment|// while (iter.hasNext())
comment|// addSpiresURL(iter.next());
comment|// }
comment|/*      * @see net.sf.jabref.imports.EntryFetcher      */
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
block|{
return|return
literal|"Spires.html"
return|;
block|}
annotation|@
name|Override
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
block|{
return|return
literal|"INSPIRE"
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
comment|// we have no additional options
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
name|Globals
operator|.
name|menuTitle
argument_list|(
name|getKeyName
argument_list|()
argument_list|)
return|;
block|}
comment|/*      * @see net.sf.jabref.gui.ImportInspectionDialog.CallBack      */
DECL|method|cancelled ()
specifier|public
name|void
name|cancelled
parameter_list|()
block|{     }
DECL|method|done (int entriesImported)
specifier|public
name|void
name|done
parameter_list|(
name|int
name|entriesImported
parameter_list|)
block|{     }
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{     }
comment|/*      * @see java.lang.Runnable      */
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
try|try
block|{
name|frame
operator|.
name|setStatus
argument_list|(
literal|"Fetching entries from Inspire"
argument_list|)
expr_stmt|;
comment|/* query the archive and load the results into the BibtexEntry */
name|BibtexDatabase
name|bd
init|=
name|importInspireEntries
argument_list|(
name|query
argument_list|,
name|frame
argument_list|)
decl_stmt|;
comment|/* addSpiresURLtoDatabase(bd); */
name|frame
operator|.
name|setStatus
argument_list|(
literal|"Adding fetched entries"
argument_list|)
expr_stmt|;
comment|/* add the entry to the inspection dialog */
if|if
condition|(
name|bd
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|bd
operator|.
name|getEntries
argument_list|()
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
block|}
comment|/* update the dialogs progress bar */
comment|// dialog.setProgress(i + 1, keys.length);
comment|/* inform the inspection dialog, that we're done */
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|frame
operator|.
name|showMessage
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error while fetching from Inspire: "
argument_list|)
operator|+
name|e
operator|.
name|getMessage
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
literal|true
return|;
block|}
block|}
end_class

end_unit

