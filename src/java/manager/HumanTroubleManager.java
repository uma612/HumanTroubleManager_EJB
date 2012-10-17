/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author DELL
 */
@MessageDriven(mappedName = "jms/Topic", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class HumanTroubleManager implements MessageListener {
    static final Logger logger = Logger.getLogger("HumanTroubleManager");
    @Resource
    private MessageDrivenContext mdc;
    
    public HumanTroubleManager() {
    }
    
    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                logger.info("MESSAGE BEAN: Message received: " + msg.getText());
                /////////////
                String popMessage = "Damage Report!!!";
                JFrame frame = new JFrame();
                frame.setSize(300,125);
                frame.setLayout(new GridBagLayout());
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy = 0;
                constraints.weightx = 1.0f;
                constraints.weighty = 1.0f;
                constraints.insets = new Insets(5, 5, 5, 5);
                constraints.fill = GridBagConstraints.BOTH;
                JLabel headingLabel = new JLabel(popMessage);
                headingLabel.setOpaque(false);
                frame.add(headingLabel, constraints);
                constraints.gridx++;
                constraints.weightx = 0f;
                constraints.weighty = 0f;
                constraints.fill = GridBagConstraints.NONE;
                constraints.anchor = GridBagConstraints.NORTH;
                JButton cloesButton = new JButton("X");
                cloesButton.setMargin(new Insets(1, 4, 1, 4));
                cloesButton.setFocusable(false);
                frame.add(cloesButton, constraints);
                constraints.gridx = 0;
                constraints.gridy++;
                constraints.weightx = 1.0f;
                constraints.weighty = 1.0f;
                constraints.insets = new Insets(5, 5, 5, 5);
                constraints.fill = GridBagConstraints.BOTH;
                JLabel messageLabel = new JLabel("<HtMl>"+message);
                frame.add(messageLabel, constraints);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);

                
                
                //////////////
            } else {
                logger.warning(
                        "Message of wrong type: "
                        + message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }
}
