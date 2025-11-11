import styles from './HomeButton.module.css';

const HomeButton = () => {
    return (
        <div className={styles.navContainer}>
            <button className={styles.homeButton}>Home</button>
        </div>
    )
}

export default HomeButton;