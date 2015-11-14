begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.sql.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Connection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|ResultSet
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
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|*
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
name|gui
operator|.
name|actions
operator|.
name|MnemonicAwareAction
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
name|gui
operator|.
name|util
operator|.
name|PositionWindow
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
name|gui
operator|.
name|worker
operator|.
name|AbstractWorker
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
name|database
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
name|JabRefPreferences
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
name|MetaData
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
name|util
operator|.
name|Util
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
name|sql
operator|.
name|DBConnectDialog
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
name|sql
operator|.
name|DBExporterAndImporterFactory
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
name|sql
operator|.
name|DBImportExportDialog
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
name|sql
operator|.
name|DBStrings
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
name|sql
operator|.
name|SQLUtil
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA. User: alver Date: Mar 27, 2008 Time: 6:09:08 PM To change this template use File | Settings  * | File Templates.  *  * Jan. 20th Changed to accomodate the new way to connect to DB and also to show the exceptions and to display more than  * one DB imported (by ifsteinm)  *  */
end_comment

begin_class
DECL|class|DbImportAction
specifier|public
class|class
name|DbImportAction
extends|extends
name|AbstractWorker
block|{
DECL|field|database
specifier|private
name|BibtexDatabase
name|database
decl_stmt|;
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|field|connectToDB
specifier|private
name|boolean
name|connectToDB
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|dbs
specifier|private
name|DBStrings
name|dbs
decl_stmt|;
DECL|field|databases
specifier|private
name|ArrayList
argument_list|<
name|Object
index|[]
argument_list|>
name|databases
decl_stmt|;
DECL|method|DbImportAction (JabRefFrame frame)
specifier|public
name|DbImportAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
DECL|method|getAction ()
specifier|public
name|AbstractAction
name|getAction
parameter_list|()
block|{
return|return
operator|new
name|DbImpAction
argument_list|()
return|;
block|}
DECL|class|DbImpAction
class|class
name|DbImpAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|method|DbImpAction ()
specifier|public
name|DbImpAction
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Import from external SQL database"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
try|try
block|{
name|Util
operator|.
name|runAbstractWorker
argument_list|(
name|DbImportAction
operator|.
name|this
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|throwable
parameter_list|)
block|{
name|throwable
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|// run first, in EDT:
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|dbs
operator|=
operator|new
name|DBStrings
argument_list|()
expr_stmt|;
name|dbs
operator|.
name|initialize
argument_list|()
expr_stmt|;
name|DBConnectDialog
name|dbd
init|=
operator|new
name|DBConnectDialog
argument_list|(
name|frame
argument_list|,
name|dbs
argument_list|)
decl_stmt|;
name|dbs
operator|=
name|dbd
operator|.
name|getDBStrings
argument_list|()
expr_stmt|;
comment|// panel.metaData().getDBStrings();
comment|// get DBStrings from user if necessary
if|if
condition|(
operator|!
name|dbs
operator|.
name|isConfigValid
argument_list|()
condition|)
block|{
comment|// init DB strings if necessary
if|if
condition|(
operator|!
name|dbs
operator|.
name|isInitialized
argument_list|()
condition|)
block|{
name|dbs
operator|.
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|// show connection dialog
name|dbd
operator|=
operator|new
name|DBConnectDialog
argument_list|(
name|frame
argument_list|,
name|dbs
argument_list|)
expr_stmt|;
name|PositionWindow
operator|.
name|placeDialog
argument_list|(
name|dbd
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|dbd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|connectToDB
operator|=
name|dbd
operator|.
name|getConnectToDB
argument_list|()
expr_stmt|;
comment|// store database strings
if|if
condition|(
name|connectToDB
condition|)
block|{
name|dbs
operator|=
name|dbd
operator|.
name|getDBStrings
argument_list|()
expr_stmt|;
name|dbd
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|connectToDB
operator|=
literal|true
expr_stmt|;
block|}
block|}
comment|// run second, on a different thread:
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|performImport
argument_list|()
expr_stmt|;
block|}
DECL|method|performImport ()
specifier|private
name|void
name|performImport
parameter_list|()
block|{
if|if
condition|(
name|connectToDB
condition|)
block|{
try|try
block|{
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Attempting SQL import..."
argument_list|)
argument_list|)
expr_stmt|;
name|DBExporterAndImporterFactory
name|factory
init|=
operator|new
name|DBExporterAndImporterFactory
argument_list|()
decl_stmt|;
name|DBImporter
name|importer
init|=
name|factory
operator|.
name|getImporter
argument_list|(
name|dbs
operator|.
name|getServerType
argument_list|()
argument_list|)
decl_stmt|;
try|try
init|(
name|Connection
name|conn
init|=
name|importer
operator|.
name|connectToDB
argument_list|(
name|dbs
argument_list|)
init|)
block|{
try|try
init|(
name|ResultSet
name|rs
init|=
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
name|conn
argument_list|,
literal|"jabref_database"
argument_list|)
init|)
block|{
name|Vector
argument_list|<
name|String
argument_list|>
name|v
decl_stmt|;
name|Vector
argument_list|<
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|>
name|matrix
init|=
operator|new
name|Vector
argument_list|<>
argument_list|()
decl_stmt|;
while|while
condition|(
name|rs
operator|.
name|next
argument_list|()
condition|)
block|{
name|v
operator|=
operator|new
name|Vector
argument_list|<>
argument_list|()
expr_stmt|;
name|v
operator|.
name|add
argument_list|(
name|rs
operator|.
name|getString
argument_list|(
literal|"database_name"
argument_list|)
argument_list|)
expr_stmt|;
name|matrix
operator|.
name|add
argument_list|(
name|v
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|matrix
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|DBImportExportDialog
name|dialogo
init|=
operator|new
name|DBImportExportDialog
argument_list|(
name|frame
argument_list|,
name|matrix
argument_list|,
name|DBImportExportDialog
operator|.
name|DialogType
operator|.
name|IMPORTER
argument_list|)
decl_stmt|;
if|if
condition|(
name|dialogo
operator|.
name|removeAction
condition|)
block|{
name|String
name|dbName
init|=
name|dialogo
operator|.
name|selectedDB
decl_stmt|;
name|importer
operator|.
name|removeDB
argument_list|(
name|dialogo
argument_list|,
name|dbName
argument_list|,
name|conn
argument_list|,
name|metaData
argument_list|)
expr_stmt|;
name|performImport
argument_list|()
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|dialogo
operator|.
name|moreThanOne
condition|)
block|{
name|databases
operator|=
name|importer
operator|.
name|performImport
argument_list|(
name|dbs
argument_list|,
name|dialogo
operator|.
name|listOfDBs
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
index|[]
name|res
range|:
name|databases
control|)
block|{
name|database
operator|=
operator|(
name|BibtexDatabase
operator|)
name|res
index|[
literal|0
index|]
expr_stmt|;
name|metaData
operator|=
operator|(
name|MetaData
operator|)
name|res
index|[
literal|1
index|]
expr_stmt|;
name|dbs
operator|.
name|isConfigValid
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 databases will be imported"
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|databases
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Importing cancelled"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"There are no available databases to be imported"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import from SQL database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|String
name|preamble
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not import from SQL database for the following reason:"
argument_list|)
decl_stmt|;
name|String
name|errorMessage
init|=
name|SQLUtil
operator|.
name|getExceptionMessage
argument_list|(
name|ex
argument_list|)
decl_stmt|;
name|dbs
operator|.
name|isConfigValid
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|preamble
argument_list|)
operator|+
literal|'\n'
operator|+
name|errorMessage
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import from SQL database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error importing from database"
argument_list|)
argument_list|)
expr_stmt|;
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|// run third, on EDT:
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
if|if
condition|(
name|databases
operator|==
literal|null
condition|)
block|{
return|return;
block|}
for|for
control|(
name|Object
index|[]
name|res
range|:
name|databases
control|)
block|{
name|database
operator|=
operator|(
name|BibtexDatabase
operator|)
name|res
index|[
literal|0
index|]
expr_stmt|;
name|metaData
operator|=
operator|(
name|MetaData
operator|)
name|res
index|[
literal|1
index|]
expr_stmt|;
if|if
condition|(
name|database
operator|!=
literal|null
condition|)
block|{
name|BasePanel
name|pan
init|=
name|frame
operator|.
name|addTab
argument_list|(
name|database
argument_list|,
literal|null
argument_list|,
name|metaData
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_ENCODING
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|pan
operator|.
name|metaData
argument_list|()
operator|.
name|setDBStrings
argument_list|(
name|dbs
argument_list|)
expr_stmt|;
name|frame
operator|.
name|setTabTitle
argument_list|(
name|pan
argument_list|,
name|res
index|[
literal|2
index|]
operator|+
literal|"(Imported)"
argument_list|,
literal|"Imported DB"
argument_list|)
expr_stmt|;
name|pan
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Imported %0 databases successfully"
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|databases
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

