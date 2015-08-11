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

begin_comment
comment|/**  *   * This class allows to access the Slac SPIRES database.  *   * It can either be a GeneralFetcher to pose requests to the database or fetch  * individual entries.  *   * @author Fedor Bezrukov  *   * @version $Id$  *   */
end_comment

begin_class
DECL|class|SPIRESFetcher
specifier|public
class|class
name|SPIRESFetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|spiresHost
specifier|private
specifier|static
specifier|final
name|String
name|spiresHost
init|=
literal|"www-spires.slac.stanford.edu"
decl_stmt|;
DECL|method|SPIRESFetcher ()
specifier|public
name|SPIRESFetcher
parameter_list|()
block|{     }
comment|/**      * Construct the query URL      *       * @param key      *            The key of the OAI2 entry that the url should poitn to.      *       * @return a String denoting the query URL      */
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
return|return
literal|"http://"
operator|+
name|SPIRESFetcher
operator|.
name|spiresHost
operator|+
literal|"/"
operator|+
literal|"spires/find/hep/www"
operator|+
literal|"?"
operator|+
literal|"rawcmd=find+"
operator|+
name|identifier
operator|+
literal|"&FORMAT=WWWBRIEFBIBTEX&SEQUENCE="
return|;
block|}
comment|/**      * Constructs a SPIRES query url from slaccitation field      *       * @param slaccitation      * @return query string      */
DECL|method|constructUrlFromSlaccitation (String slaccitation)
specifier|public
specifier|static
name|String
name|constructUrlFromSlaccitation
parameter_list|(
name|String
name|slaccitation
parameter_list|)
block|{
name|String
name|cmd
init|=
literal|"j"
decl_stmt|;
name|String
name|key
init|=
name|slaccitation
operator|.
name|replaceAll
argument_list|(
literal|"^%%CITATION = "
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|";%%$"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
if|if
condition|(
name|key
operator|.
name|matches
argument_list|(
literal|"^\\w*-\\w*[ /].*"
argument_list|)
condition|)
block|{
name|cmd
operator|=
literal|"eprint"
expr_stmt|;
block|}
try|try
block|{
name|key
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
name|ignored
parameter_list|)
block|{         }
return|return
literal|"http://"
operator|+
name|SPIRESFetcher
operator|.
name|spiresHost
operator|+
literal|"/"
operator|+
literal|"spires/find/hep/www"
operator|+
literal|"?"
operator|+
literal|"rawcmd=find+"
operator|+
name|cmd
operator|+
literal|"+"
operator|+
name|key
return|;
block|}
comment|/**      * Construct an SPIRES query url from eprint field      *       * @param eprint      * @return query string      */
DECL|method|constructUrlFromEprint (String eprint)
specifier|public
specifier|static
name|String
name|constructUrlFromEprint
parameter_list|(
name|String
name|eprint
parameter_list|)
block|{
name|String
name|key
init|=
name|eprint
operator|.
name|replaceAll
argument_list|(
literal|" [.*]$"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
try|try
block|{
name|key
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
return|return
literal|"http://"
operator|+
name|SPIRESFetcher
operator|.
name|spiresHost
operator|+
literal|"/"
operator|+
literal|"spires/find/hep/www"
operator|+
literal|"?"
operator|+
literal|"rawcmd=find+eprint+"
operator|+
name|key
return|;
block|}
comment|/**      * Import an entry from an OAI2 archive. The BibtexEntry provided has to      * have the field OAI2_IDENTIFIER_FIELD set to the search string.      *       * @param key      *            The OAI2 key to fetch from ArXiv.      * @return The imnported BibtexEntry or null if none.      */
DECL|method|importSpiresEntries (String key, OutputPrinter frame)
specifier|private
name|BibtexDatabase
name|importSpiresEntries
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
operator|(
name|HttpURLConnection
operator|)
operator|new
name|URL
argument_list|(
name|url
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
name|SPIRESBibtexFilterReader
name|reader
init|=
operator|new
name|SPIRESBibtexFilterReader
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
name|Localization
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
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"An Error occurred while fetching from SPIRES source (%0):"
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
name|Localization
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
literal|"SPIRES"
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
name|Localization
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
literal|"Fetching entries from Spires"
argument_list|)
expr_stmt|;
comment|/* query the archive and load the results into the BibtexEntry */
name|BibtexDatabase
name|bd
init|=
name|importSpiresEntries
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from Spires: "
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

