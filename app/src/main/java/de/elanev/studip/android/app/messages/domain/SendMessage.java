/*
 * Copyright (c) 2016 ELAN e.V.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 */

package de.elanev.studip.android.app.messages.domain;

import javax.inject.Inject;

import de.elanev.studip.android.app.base.UseCase;
import de.elanev.studip.android.app.base.domain.executor.PostExecutionThread;
import de.elanev.studip.android.app.base.domain.executor.ThreadExecutor;
import de.elanev.studip.android.app.base.internal.di.PerActivity;
import de.elanev.studip.android.app.base.internal.di.PerFragment;
import de.elanev.studip.android.app.messages.data.repository.MessagesEntityDataMapper;
import rx.Observable;
import rx.Subscriber;

/**
 * @author joern
 */
@PerFragment
public class SendMessage extends UseCase {
  private final MessagesRepository messageRepository;
  private Message message;

  @Inject protected SendMessage(MessagesRepository messagesRepository, ThreadExecutor
      threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);

    this.messageRepository = messagesRepository;
  }

  @Override public void execute(Subscriber subscriber) {
    if (this.message == null) {
      return;
    }

    if (message.getReceiver() == null) {
      subscriber.onError(new IllegalStateException("Message receiver must not be null!"));
    }

    super.execute(subscriber);
  }

  @Override protected Observable buildUseCaseObservable() {
    return messageRepository.send(this.message);
  }

  public void setMessage(Message message) {
    this.message = message;
  }
}
