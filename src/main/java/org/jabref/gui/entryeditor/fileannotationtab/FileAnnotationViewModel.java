begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor.fileannotationtab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
operator|.
name|fileannotationtab
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleStringProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|StringProperty
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|RemoveHyphenatedNewlinesFormatter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|RemoveNewlinesFormatter
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|pdf
operator|.
name|FileAnnotation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|pdf
operator|.
name|FileAnnotationType
import|;
end_import

begin_class
DECL|class|FileAnnotationViewModel
specifier|public
class|class
name|FileAnnotationViewModel
block|{
DECL|field|annotation
specifier|private
specifier|final
name|FileAnnotation
name|annotation
decl_stmt|;
DECL|field|author
specifier|private
name|StringProperty
name|author
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|page
specifier|private
name|StringProperty
name|page
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|date
specifier|private
name|StringProperty
name|date
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|content
specifier|private
name|StringProperty
name|content
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|marking
specifier|private
name|StringProperty
name|marking
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|method|FileAnnotationViewModel (FileAnnotation annotation)
specifier|public
name|FileAnnotationViewModel
parameter_list|(
name|FileAnnotation
name|annotation
parameter_list|)
block|{
name|this
operator|.
name|annotation
operator|=
name|annotation
expr_stmt|;
name|author
operator|.
name|set
argument_list|(
name|annotation
operator|.
name|getAuthor
argument_list|()
argument_list|)
expr_stmt|;
name|page
operator|.
name|set
argument_list|(
name|Integer
operator|.
name|toString
argument_list|(
name|annotation
operator|.
name|getPage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|date
operator|.
name|set
argument_list|(
name|annotation
operator|.
name|getTimeModified
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|'T'
argument_list|,
literal|' '
argument_list|)
argument_list|)
expr_stmt|;
name|setupContentProperties
argument_list|(
name|annotation
argument_list|)
expr_stmt|;
block|}
DECL|method|setupContentProperties (FileAnnotation annotation)
specifier|private
name|void
name|setupContentProperties
parameter_list|(
name|FileAnnotation
name|annotation
parameter_list|)
block|{
if|if
condition|(
name|annotation
operator|.
name|hasLinkedAnnotation
argument_list|()
condition|)
block|{
name|this
operator|.
name|content
operator|.
name|set
argument_list|(
name|annotation
operator|.
name|getLinkedFileAnnotation
argument_list|()
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|annotationContent
init|=
name|annotation
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|String
name|illegibleTextMessage
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"The marked area does not contain any legible text!"
argument_list|)
decl_stmt|;
name|this
operator|.
name|marking
operator|.
name|set
argument_list|(
name|annotationContent
operator|.
name|isEmpty
argument_list|()
condition|?
name|illegibleTextMessage
else|:
name|annotationContent
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|content
init|=
name|annotation
operator|.
name|getContent
argument_list|()
decl_stmt|;
comment|// remove newlines&& hyphens before linebreaks
name|content
operator|=
operator|new
name|RemoveHyphenatedNewlinesFormatter
argument_list|()
operator|.
name|format
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|content
operator|=
operator|new
name|RemoveNewlinesFormatter
argument_list|()
operator|.
name|format
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|this
operator|.
name|content
operator|.
name|set
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|this
operator|.
name|marking
operator|.
name|set
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getAuthor ()
specifier|public
name|String
name|getAuthor
parameter_list|()
block|{
return|return
name|author
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|getPage ()
specifier|public
name|String
name|getPage
parameter_list|()
block|{
return|return
name|page
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|getDate ()
specifier|public
name|String
name|getDate
parameter_list|()
block|{
return|return
name|date
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|getContent ()
specifier|public
name|String
name|getContent
parameter_list|()
block|{
return|return
name|content
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|pageProperty ()
specifier|public
name|StringProperty
name|pageProperty
parameter_list|()
block|{
return|return
name|page
return|;
block|}
DECL|method|dateProperty ()
specifier|public
name|StringProperty
name|dateProperty
parameter_list|()
block|{
return|return
name|date
return|;
block|}
DECL|method|contentProperty ()
specifier|public
name|StringProperty
name|contentProperty
parameter_list|()
block|{
return|return
name|content
return|;
block|}
DECL|method|markingProperty ()
specifier|public
name|StringProperty
name|markingProperty
parameter_list|()
block|{
return|return
name|marking
return|;
block|}
DECL|method|authorProperty ()
specifier|public
name|StringProperty
name|authorProperty
parameter_list|()
block|{
return|return
name|author
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|annotation
operator|.
name|hasLinkedAnnotation
argument_list|()
operator|&&
name|this
operator|.
name|getContent
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|FileAnnotationType
operator|.
name|UNDERLINE
operator|.
name|equals
argument_list|(
name|annotation
operator|.
name|getAnnotationType
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Empty Underline"
argument_list|)
return|;
block|}
if|if
condition|(
name|FileAnnotationType
operator|.
name|HIGHLIGHT
operator|.
name|equals
argument_list|(
name|annotation
operator|.
name|getAnnotationType
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Empty Highlight"
argument_list|)
return|;
block|}
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Empty Marking"
argument_list|)
return|;
block|}
if|if
condition|(
name|FileAnnotationType
operator|.
name|UNDERLINE
operator|.
name|equals
argument_list|(
name|annotation
operator|.
name|getAnnotationType
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Underline"
argument_list|)
operator|+
literal|": "
operator|+
name|this
operator|.
name|getContent
argument_list|()
return|;
block|}
if|if
condition|(
name|FileAnnotationType
operator|.
name|HIGHLIGHT
operator|.
name|equals
argument_list|(
name|annotation
operator|.
name|getAnnotationType
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Highlight"
argument_list|)
operator|+
literal|": "
operator|+
name|this
operator|.
name|getContent
argument_list|()
return|;
block|}
return|return
name|super
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|marking
operator|.
name|get
argument_list|()
return|;
block|}
block|}
end_class

end_unit

