import {View, Text, LogBox, AppRegistry} from 'react-native';
import React, {useEffect} from 'react';
import {Provider} from 'react-redux';
import store from './src/store';
import {increment} from './src/slice/counter';
import AppInner from './AppInner';

function App() {
  LogBox.ignoreLogs(['new NativeEventEmitter']); // Ignore log notification by message
  LogBox.ignoreAllLogs(); //Ignore all log notifications

  return (
    <Provider store={store}>
      <AppInner />
    </Provider>
  );
}

const HeadlessTask = async () => {
  console.log('실행');
  store.dispatch(increment());
};

AppRegistry.registerHeadlessTask('HeadlessTaskService', () => HeadlessTask);

export default App;
