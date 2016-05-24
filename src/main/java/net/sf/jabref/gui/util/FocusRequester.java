begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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

begin_class
DECL|class|FocusRequester
specifier|public
class|class
name|FocusRequester
implements|implements
name|Runnable
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
name|FocusRequester
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|comp
specifier|private
specifier|final
name|Component
name|comp
decl_stmt|;
DECL|method|FocusRequester (Component comp)
specifier|public
name|FocusRequester
parameter_list|(
name|Component
name|comp
parameter_list|)
block|{
if|if
condition|(
name|comp
operator|==
literal|null
condition|)
block|{
name|Thread
operator|.
name|dumpStack
argument_list|()
expr_stmt|;
block|}
name|this
operator|.
name|comp
operator|=
name|comp
expr_stmt|;
name|run
argument_list|()
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
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"requesting focus for "
operator|+
name|comp
argument_list|)
expr_stmt|;
name|comp
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

