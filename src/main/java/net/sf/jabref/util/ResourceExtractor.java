begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
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
name|IOException
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|net
operator|.
name|MonitoredURLDownload
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRef
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
name|Worker
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
name|net
operator|.
name|URLDownload
import|;
end_import

begin_comment
comment|/**  * This class performs the somewhat weird action of extracting a file from within the running JabRef jar,  * and storing it to the given File. It may prove useful e.g. for extracting Endnote export/import filters which  * are needed for full integration with the export filter in JabRef, so we can bundle these for the user even though  * they are not used by JabRef directly.  *  *  *  * @author alver  */
end_comment

begin_class
DECL|class|ResourceExtractor
specifier|public
class|class
name|ResourceExtractor
implements|implements
name|Worker
block|{
DECL|field|resource
specifier|private
specifier|final
name|URL
name|resource
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|Component
name|parent
decl_stmt|;
DECL|field|destination
specifier|private
specifier|final
name|File
name|destination
decl_stmt|;
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
name|ResourceExtractor
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/** Creates a new instance of ResourceExtractor */
DECL|method|ResourceExtractor (final Component parent, final String filename, File destination)
specifier|public
name|ResourceExtractor
parameter_list|(
specifier|final
name|Component
name|parent
parameter_list|,
specifier|final
name|String
name|filename
parameter_list|,
name|File
name|destination
parameter_list|)
block|{
name|resource
operator|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|filename
argument_list|)
expr_stmt|;
comment|//System.out.println(filename+"\n"+resource);
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|this
operator|.
name|destination
operator|=
name|destination
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|URLDownload
name|ud
init|=
name|MonitoredURLDownload
operator|.
name|buildMonitoredDownload
argument_list|(
name|parent
argument_list|,
name|resource
argument_list|)
decl_stmt|;
try|try
block|{
name|ud
operator|.
name|downloadToFile
argument_list|(
name|destination
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Error extracting resource: "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

