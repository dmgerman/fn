begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.remote
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|remote
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
name|importer
operator|.
name|ParserResult
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
name|remote
operator|.
name|server
operator|.
name|MessageHandler
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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

begin_class
DECL|class|JabRefMessageHandler
specifier|public
class|class
name|JabRefMessageHandler
implements|implements
name|MessageHandler
block|{
DECL|field|jabRef
specifier|private
specifier|final
name|JabRef
name|jabRef
decl_stmt|;
DECL|method|JabRefMessageHandler (JabRef jabRef)
specifier|public
name|JabRefMessageHandler
parameter_list|(
name|JabRef
name|jabRef
parameter_list|)
block|{
name|this
operator|.
name|jabRef
operator|=
name|jabRef
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|handleMessage (String message)
specifier|public
name|void
name|handleMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|Optional
argument_list|<
name|Vector
argument_list|<
name|ParserResult
argument_list|>
argument_list|>
name|loaded
init|=
name|jabRef
operator|.
name|processArguments
argument_list|(
name|message
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|loaded
operator|.
name|isPresent
argument_list|()
operator|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Could not start JabRef with arguments "
operator|+
name|message
argument_list|)
throw|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|loaded
operator|.
name|get
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|ParserResult
name|pr
init|=
name|loaded
operator|.
name|get
argument_list|()
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|JabRef
operator|.
name|mainFrame
operator|.
name|addParserResult
argument_list|(
name|pr
argument_list|,
name|i
operator|==
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

