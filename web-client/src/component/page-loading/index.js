import React from 'react';
import {Spin} from 'antd';

import styles from './style.scss';

class PageLoading extends React.PureComponent {
    render() {
        return (
            <div className={styles.container}>
                <Spin size="large" />
            </div>
        );
    }
}

export default PageLoading;
