begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* All programs in this directory and subdirectories are published under the  GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it  under the terms of the GNU General Public License as published by the Free  Software Foundation; either version 2 of the License, or (at your option)  any later version.  This program is distributed in the hope that it will be useful, but WITHOUT  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for  more details.  You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc., 59  Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|AbstractUndoableEdit
import|;
end_import

begin_comment
comment|/**  * @author jzieren  *   * TODO To change the template for this generated type comment go to Window -  * Preferences - Java - Code Style - Code Templates  */
end_comment

begin_class
DECL|class|UndoableChangeAssignment
specifier|public
class|class
name|UndoableChangeAssignment
extends|extends
name|AbstractUndoableEdit
block|{
DECL|field|m_previousAssignmentBackup
specifier|private
specifier|final
name|Set
name|m_previousAssignmentBackup
decl_stmt|;
DECL|field|m_currentAssignmentBackup
specifier|private
specifier|final
name|Set
name|m_currentAssignmentBackup
decl_stmt|;
DECL|field|m_currentAssignmentReference
specifier|private
specifier|final
name|Set
name|m_currentAssignmentReference
decl_stmt|;
comment|/**      *       * @param previousAssignment      *            The set of assigned entries before the change (this is      *            copied).      *       * @param currentAssignment      *            A reference to the actual set of assignments      */
DECL|method|UndoableChangeAssignment (Set previousAssignment, Set currentAssignment)
specifier|public
name|UndoableChangeAssignment
parameter_list|(
name|Set
name|previousAssignment
parameter_list|,
name|Set
name|currentAssignment
parameter_list|)
block|{
name|m_previousAssignmentBackup
operator|=
operator|new
name|HashSet
argument_list|(
name|previousAssignment
argument_list|)
expr_stmt|;
name|m_currentAssignmentReference
operator|=
name|currentAssignment
expr_stmt|;
name|m_currentAssignmentBackup
operator|=
operator|new
name|HashSet
argument_list|(
name|currentAssignment
argument_list|)
expr_stmt|;
block|}
DECL|method|getUndoPresentationName ()
specifier|public
name|String
name|getUndoPresentationName
parameter_list|()
block|{
return|return
literal|"Undo: (de)assign entries"
return|;
block|}
DECL|method|getRedoPresentationName ()
specifier|public
name|String
name|getRedoPresentationName
parameter_list|()
block|{
return|return
literal|"Redo: (de)assign entries"
return|;
block|}
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
name|super
operator|.
name|undo
argument_list|()
expr_stmt|;
name|m_currentAssignmentReference
operator|.
name|clear
argument_list|()
expr_stmt|;
name|m_currentAssignmentReference
operator|.
name|addAll
argument_list|(
name|m_previousAssignmentBackup
argument_list|)
expr_stmt|;
block|}
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
name|super
operator|.
name|redo
argument_list|()
expr_stmt|;
name|m_currentAssignmentReference
operator|.
name|clear
argument_list|()
expr_stmt|;
name|m_currentAssignmentReference
operator|.
name|addAll
argument_list|(
name|m_currentAssignmentBackup
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

