begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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

begin_comment
comment|/**  * Exception thrown if saving goes wrong. If caused by a specific  * entry, keeps track of which entry caused the problem.  */
end_comment

begin_class
DECL|class|SaveException
specifier|public
class|class
name|SaveException
extends|extends
name|Exception
block|{
comment|//~ Instance fields ////////////////////////////////////////////////////////
DECL|field|FILE_LOCKED
specifier|public
specifier|static
specifier|final
name|SaveException
name|FILE_LOCKED
init|=
operator|new
name|SaveException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not save, file locked by another JabRef instance."
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|entry
specifier|private
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|status
specifier|private
name|int
name|status
decl_stmt|;
comment|//~ Constructors ///////////////////////////////////////////////////////////
DECL|method|SaveException (String message)
specifier|public
name|SaveException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|entry
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|SaveException (String message, int status)
specifier|public
name|SaveException
parameter_list|(
name|String
name|message
parameter_list|,
name|int
name|status
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|entry
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|status
operator|=
name|status
expr_stmt|;
block|}
DECL|method|SaveException (String message, BibtexEntry entry)
specifier|public
name|SaveException
parameter_list|(
name|String
name|message
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
block|}
comment|//~ Methods ////////////////////////////////////////////////////////////////
DECL|method|getStatus ()
specifier|public
name|int
name|getStatus
parameter_list|()
block|{
return|return
name|status
return|;
block|}
DECL|method|getEntry ()
specifier|public
name|BibtexEntry
name|getEntry
parameter_list|()
block|{
return|return
name|entry
return|;
block|}
DECL|method|specificEntry ()
specifier|public
name|boolean
name|specificEntry
parameter_list|()
block|{
return|return
operator|(
name|entry
operator|!=
literal|null
operator|)
return|;
block|}
block|}
end_class

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  END OF FILE.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

end_unit

