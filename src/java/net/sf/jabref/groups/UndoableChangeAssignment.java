begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Created on 03.01.2005  *  * TODO To change the template for this generated file go to  * Window - Preferences - Java - Code Style - Code Templates  */
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
comment|/**  * @author zieren  *   * TODO To change the template for this generated type comment go to Window -  * Preferences - Java - Code Style - Code Templates  */
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

