package org.pentaho.reporting.designer.core.versionchecker;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import org.pentaho.reporting.designer.core.util.HyperLink;
import org.pentaho.reporting.designer.core.widgets.HyperlinkHandler;
import org.pentaho.reporting.libraries.designtime.swing.CommonDialog;

public class VersionCheckerEnableDialog extends CommonDialog
{
  public VersionCheckerEnableDialog(final Frame owner)
      throws HeadlessException
  {
    super(owner);
    init();
  }

  public VersionCheckerEnableDialog(final Dialog owner)
      throws HeadlessException
  {
    super(owner);
    init();
  }

  public VersionCheckerEnableDialog()
  {
    init();
  }

  protected void init()
  {
    setTitle(Messages.getInstance().getString("VersionCheckerUtility.Title"));
    setModal(true);

    // build button panel
    super.init();

    getConfirmAction().putValue(Action.NAME, Messages.getInstance().getString("VersionCheckerUtility.Yes"));// NON-NLS
    getCancelAction().putValue(Action.NAME, Messages.getInstance().getString("VersionCheckerUtility.No"));// NON-NLS
  }

  protected String getDialogId()
  {
    return "ReportDesigner.Core.VersionCheckerEnable";
  }

  protected Component createContentPane()
  {
    Font font = UIManager.getFont("Label.font"); // NON-NLS
    if (font == null)
    {
      font = new Font("Dialog", Font.PLAIN, 10);// NON-NLS
    }

    final JPanel contentPane = new JPanel();
    contentPane.setLayout(new GridBagLayout());
    final JTextPane messageLabel = new JTextPane();
    messageLabel.setFont(font.deriveFont(Font.BOLD));
    messageLabel.setText(Messages.getInstance().getString("VersionCheckerUtility.Message"));
    messageLabel.setFocusable(false);
    messageLabel.setBackground(null);

    final String url = Messages.getInstance().getString("VersionCheckerUtility.URL");
    final HyperLink linkLbl = new HyperLink(url);
    linkLbl.addMouseListener(new HyperlinkHandler(url, linkLbl));

    final String questionText = Messages.getInstance().getString("VersionCheckerUtility.Question");
    final JTextPane questionLabel = new JTextPane();
    questionLabel.setFont(font.deriveFont(Font.BOLD));
    questionLabel.setText(questionText);
    questionLabel.setBackground(null);
    questionLabel.setFocusable(false);

    final GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(10, 10, 0, 10);
    c.gridx = 0;
    c.gridy = 0;
    c.anchor = GridBagConstraints.SOUTHWEST;
    contentPane.add(messageLabel, c);

    c.insets = new Insets(0, 15, 0, 10);
    c.gridx = 0;
    c.gridy = 1;
    c.anchor = GridBagConstraints.NORTHWEST;
    contentPane.add(linkLbl, c);

    c.insets = new Insets(0, 10, 10, 10);
    c.gridx = 0;
    c.gridy = 2;
    c.anchor = GridBagConstraints.SOUTHWEST;
    contentPane.add(questionLabel, c);

    return contentPane;
  }

  public boolean performEdit()
  {
    return super.performEdit();
  }
}
