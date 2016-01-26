begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.sql
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
package|;
end_package

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
name|BaseAction
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
name|BasePanel
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
name|sql
operator|.
name|exporter
operator|.
name|DBExporter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
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
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA. User: alver Date: Mar 27, 2008 Time: 6:05:13 PM To  * change this template use File | Settings | File Templates.  *  * Jan 20th Adjusted to accomodate changes on SQL Exporter module by ifsteinm  *  */
end_comment

begin_class
DECL|class|DbConnectAction
specifier|public
class|class
name|DbConnectAction
implements|implements
name|BaseAction
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|method|DbConnectAction (BasePanel panel)
specifier|public
name|DbConnectAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
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
specifier|private
class|class
name|DbImpAction
extends|extends
name|AbstractAction
block|{
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
name|action
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
name|DBStrings
name|dbs
init|=
name|panel
operator|.
name|getLoadedDatabase
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getDBStrings
argument_list|()
decl_stmt|;
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
name|DBConnectDialog
name|dbd
init|=
operator|new
name|DBConnectDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|dbs
argument_list|)
decl_stmt|;
name|PositionWindow
operator|.
name|placeDialog
argument_list|(
name|dbd
argument_list|,
name|panel
argument_list|)
expr_stmt|;
name|dbd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// connect to database to test DBStrings
if|if
condition|(
name|dbd
operator|.
name|getConnectToDB
argument_list|()
condition|)
block|{
name|dbs
operator|=
name|dbd
operator|.
name|getDBStrings
argument_list|()
expr_stmt|;
try|try
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Establishing SQL connection..."
argument_list|)
argument_list|)
expr_stmt|;
name|DBExporter
name|exporter
init|=
operator|(
operator|new
name|DBExporterAndImporterFactory
argument_list|()
operator|)
operator|.
name|getExporter
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
name|exporter
operator|.
name|connectToDB
argument_list|(
name|dbs
argument_list|)
init|)
block|{
comment|// Nothing
block|}
name|dbs
operator|.
name|isConfigValid
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"SQL connection established."
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
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
name|String
name|preamble
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not connect to SQL database for the following reason:"
argument_list|)
decl_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|output
argument_list|(
name|preamble
operator|+
literal|"  "
operator|+
name|errorMessage
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|preamble
operator|+
literal|'\n'
operator|+
name|errorMessage
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connect to SQL database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|panel
operator|.
name|getLoadedDatabase
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|setDBStrings
argument_list|(
name|dbs
argument_list|)
expr_stmt|;
name|dbd
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

