begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 David Weitzman  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_class
DECL|class|KeyCollisionException
specifier|public
class|class
name|KeyCollisionException
extends|extends
name|Exception
block|{
DECL|method|KeyCollisionException ()
specifier|public
name|KeyCollisionException
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|KeyCollisionException (String msg)
specifier|public
name|KeyCollisionException
parameter_list|(
name|String
name|msg
parameter_list|)
block|{
name|super
argument_list|(
name|msg
argument_list|)
expr_stmt|;
block|}
DECL|method|KeyCollisionException (String msg, Throwable exception)
specifier|public
name|KeyCollisionException
parameter_list|(
name|String
name|msg
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|super
argument_list|(
name|msg
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
DECL|method|KeyCollisionException (Throwable exception)
specifier|public
name|KeyCollisionException
parameter_list|(
name|Throwable
name|exception
parameter_list|)
block|{
name|super
argument_list|(
name|exception
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

